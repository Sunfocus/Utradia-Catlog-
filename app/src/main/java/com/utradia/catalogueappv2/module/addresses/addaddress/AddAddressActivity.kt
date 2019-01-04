package com.utradia.catalogueappv2.module.addresses.addaddress

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity

import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.AddressDetailResponse
import com.utradia.catalogueappv2.model.CitiesResponse
import com.utradia.catalogueappv2.model.RegionsResponse
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_addaddress.*
import javax.inject.Inject

class AddAddressActivity  : BaseActivity(),AddAddressView ,View.OnClickListener,OnRegionClicked{


    override fun onAddressDetailFound(addressResponse: AddressDetailResponse) {
        val firstName=mAppUtils.getMeNthParamInString(addressResponse.address.full_name," ",1)
        val lastNAme=mAppUtils.getMeNthParamInString(addressResponse.address.full_name," ",2)
        edtFirstName.setText(firstName)
        edtLastName.setText(lastNAme)

        edtAddress.setText(addressResponse.address.locality)
        edtRegion.setText(addressResponse.address.state_name)
        mRegionID=addressResponse.address.state
        edtCity.setText(addressResponse.address.city_name)
        mCityID=addressResponse.address.city

        edtPhone.setText(addressResponse.address.mobile_number)
        getCities(addressResponse.address.state,addressResponse.address.city_name,mCityID)


    }

    override fun onAddressAdded(message: String) {
        mAppUtils.showSuccessToast(message)
        finish()
    }

    @Inject lateinit var mAddAddressPresenter: AddAddressPresenter
    @Inject lateinit var mPrefs: PreferenceManager
    lateinit var response: RegionsResponse
     var citiesResponse: CitiesResponse?=null
    @Inject lateinit var mDialogsUtil:DialogsUtil
    private lateinit var mRegionID:String
    private lateinit var mCityID:String
    private lateinit var mIsEdit:String
    private lateinit var mAddressId:String
    private var dialog:Dialog?=null

    override val layout: Int
        get() = R.layout.activity_addaddress

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mAddAddressPresenter.injectDependencies(this)
        mAddAddressPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        mIsEdit=intent.getStringExtra("is_edit_extra")
        mAddressId=intent.getStringExtra("add_id_extra")
        setToolbar()
        initClicks()
        /*
         * getRegions
         * */
        getRegions()

        if(mIsEdit.equals("1",true))
        {
            mAddAddressPresenter.getAddress(mAddressId)
        }
    }

    private fun initClicks() {
        edtRegion.setOnClickListener(this)
        edtCity.setOnClickListener(this)
        btnSaveChanges.setOnClickListener(this)
    }

    private fun getRegions() {
        if (mAppUtils.isInternetConnected)
            mAddAddressPresenter.getRegions()
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }

    /*
    * get Cities by region*/
    private fun getCities(region_id:String,city:String,city_id:String) {
        if (mAppUtils.isInternetConnected)
            mAddAddressPresenter.getCities(region_id,city,city_id)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.address)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()

        mAddAddressPresenter.cancelAllAsync()
        mAddAddressPresenter.detachView()
    }



    companion object {
        fun createIntent(context: Context,add_id:String,isEdit:String): Intent {
            return Intent(context, AddAddressActivity::class.java)
                    .putExtra("add_id_extra",add_id)
                    .putExtra("is_edit_extra",isEdit)
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edtRegion-> {
                openRegionDialog()
            }
            R.id.edtCity-> {
                if(citiesResponse!=null)
                openCitiesDialog()
            }
            R.id.btnSaveChanges-> {
                addUpdateAddress()
            }
        }
    }

    /*View methods*/
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCitiesFound(response: CitiesResponse,city:String,city_id:String) {
        citiesResponse=response
        if(city.equals("",true))
        {
            mCityID=""
            edtCity.setText("")
        }
        else
        {
            mCityID=city_id
            edtCity.setText(city)
        }

    }

    override fun onCityClicked(id: String, name: String) {
        mCityID=id
        edtCity.setText(name)
        if(dialog!=null)
            dialog?.dismiss()
    }

    override fun onItemClicked(id: String, name: String) {
        /*setting region id for later use*/
        mRegionID=id
        edtRegion.setText(name)
        getCities(id,"","")
        if(dialog!=null)
            dialog?.dismiss()
    }


    override fun onRegionsFound(response: RegionsResponse) {
        this.response=response
    }

    override fun onRegionsNotFound(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }


    /*
   * opening cities dialog from region id
   * */
    private fun openCitiesDialog() {
        val layoutManager = LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        dialog = mDialogsUtil.showDialog(this, R.layout.view_region_popup)
        dialog?.show()
        val rvRegions = dialog?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvRegions)
        rvRegions?.layoutManager = layoutManager
        val mCitiesAdapter = CitiesAdapter(this,citiesResponse!!.regions, this)
        rvRegions?.adapter=mCitiesAdapter
    }

    /*Opening dialog from response
    * */
    private fun openRegionDialog() {
        val layoutManager = LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        dialog = mDialogsUtil.showDialog(this, R.layout.view_region_popup)
        dialog?.show()
        val rvRegions = dialog?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvRegions)
        rvRegions?.setLayoutManager(layoutManager)
        val mRegionsAdapter = RegionsAdapter(this,response.regions, this)
        rvRegions?.adapter=mRegionsAdapter
    }

    /*
    * Add address to db after validating
    * */
    private fun addUpdateAddress() {
        if (edtFirstName.text!!.trim().toString().isEmpty() || edtLastName.text!!.trim().toString().isEmpty() ||
                edtAddress.text!!.trim().toString().isEmpty() || mRegionID.isEmpty() ||
                mCityID.isEmpty() || edtPhone.text!!.trim().toString().isEmpty() )
        {
            mAppUtils.showToast( getString(R.string.fillallemptyfields))
        }
        else if (edtPhone.text.toString().trim().length < 10){
            mAppUtils.showToast(getString(R.string.phoneerror))
        }
        else{
            if (mAppUtils.isInternetConnected){
                if (mIsEdit.equals("1",true))
                    mAddAddressPresenter.updateAddress(mAddressId,edtFirstName.text.toString()+" "+edtLastName.text.toString()
                            ,edtPhone.text.toString(),"77",edtAddress.text.toString()
                            ,mCityID,mRegionID,"shipping")
                else
                    mAddAddressPresenter.addAddress(mPrefs.userId,edtFirstName.text.toString()+" "+edtLastName.text.toString()
                            ,edtPhone.text.toString(),"77",edtAddress.text.toString()
                            ,mCityID,mRegionID,"shipping")
            }
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
    }

}