package com.utradia.catalogueappv2.module.addresses.alladdresses

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.AddressResponse
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressActivity
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_addresses.*
import kotlinx.android.synthetic.main.view_confirm.*
import javax.inject.Inject

class AddressesActivity : BaseActivity(),AddressesView,onAddressInteracted {
    override fun onDeleteClicked(id: String, pos: Int) {
        val dialog = mDialogsUtil.showDialog(this, R.layout.view_confirm)
        dialog.show()

        dialog.txtRemove.setOnClickListener {
            dialog.dismiss()
            if (mAppUtils.isInternetConnected)
                mAddressesPresenter.deleteAddress(id)
            else
                mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
        dialog.txtCancel.setOnClickListener {
            dialog.dismiss()
        }
    }


    override fun onAddressDefault(msg:String) {
        mAppUtils.showSuccessToast(msg)
        for ((index, value) in response.address.withIndex()) {
            if (index==pos)
                value.defaultX="1"
            else
                value.defaultX="0"
        }
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onAddressError(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    override fun onEditClicked(id: String,pos:Int) {
        startActivity(AddAddressActivity.createIntent(this,id,"1"))
    }

    override fun onDefaultSelected(id: String,pos:Int) {
        this.pos=pos
        setAddressAsDefault(id)
    }

    private fun setAddressAsDefault(id: String) {
        if (mAppUtils.isInternetConnected)
            mAddressesPresenter.setDefault(id,mPrefs.userId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun onAddressFound(response: AddressResponse) {
        this.response=response

        txtNoAddress.visibility=View.GONE
        rvAddresses.visibility=View.VISIBLE
        if (mAdapter==null) {
            val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            rvAddresses.layoutManager = layoutManager
            mAdapter = AddressesAdapter(this, response.address,this)
            rvAddresses.adapter = mAdapter
        }
        else{
            mAdapter!!.setItems(response.address)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onAddressNotFound(error: String) {
        txtNoAddress.visibility=View.VISIBLE
        rvAddresses.visibility=View.GONE
    }

    override fun onAddressDeleted(msg:String) {
        mAppUtils.showSuccessToast(msg)
        response.address.removeAt(pos)
        mAdapter!!.notifyDataSetChanged()
    }

    @Inject
    lateinit var mAddressesPresenter: AddressesPresenter
    @Inject lateinit var mDialogsUtil:DialogsUtil
    @Inject
    lateinit var mPrefs: PreferenceManager
    lateinit var response: AddressResponse
    private var mAdapter :AddressesAdapter?=null
    private var pos :Int=0
    override val layout: Int
        get() = R.layout.activity_addresses

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mAddressesPresenter.injectDependencies(this)
        mAddressesPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()

    }

    override fun onResume() {
        super.onResume()
        /*get Addresses*/
        getAddresses()
    }
    /*
   * get user saved Addresses
   * */
    private fun getAddresses() {
        if (mAppUtils.isInternetConnected)
            mAddressesPresenter.getAddresses(mPrefs.userId,"shipping")
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.address)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()

        mAddressesPresenter.cancelAllAsync()
        mAddressesPresenter.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(AddAddressActivity.createIntent(this,"","0"))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, AddressesActivity::class.java)
        }

    }

}