package com.utradia.catalogueappv2.module.profile

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProfileResponse
import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressActivity
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesActivity
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import eu.inmite.android.lib.validations.form.FormValidator
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : BaseActivity() ,ProfileView, DatePickerDialog.OnDateSetListener,RadioGroup.OnCheckedChangeListener{

    @Inject
    lateinit var mPrefs: PreferenceManager

    @Inject
    lateinit var mPresenter: ProfilePresenter
    private var mDate: String? = null

    private var mGender: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        /*attaching view{@link LoginView} to our presenter*/
        mPresenter.injectDependencies(this)
        mPresenter.attachView(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        getProfileData()
        initViewsClick()

    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }
    /**
     * get layout to inflate
     */
    override val layout: Int
        get() = R.layout.activity_profile

    override fun showToolbar(): Boolean {
        return true
    }

    /**
     * get layout to inflate
     */
    override val views: List<View>?
        get() = null

    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.my_account)

        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cancelAllAsync()
        mPresenter.detachView()
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val itemCart = menu?.findItem(R.id.action_cart)
        val icon = itemCart?.icon as LayerDrawable
        if (!mPrefs.cartCount.equals("",true))
            mAppUtils.setBadgeCount(this, icon, mPrefs.cartCount)


        return super.onPrepareOptionsMenu(menu)

    }

    /*
    * get Profile data of user*/
    private fun getProfileData(){
        if (mAppUtils.isInternetConnected) {
            mAppUtils.hideSoftKeyboard(window.decorView.rootView)

            mPresenter.getProfile(mPrefs.userId)
        } else {

            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
    }

    /*View functions*/
    override fun onUserDetailsFound(response: ProfileResponse) {
        edtFirstName.setText(response.userdata.first_name)
        edtLastName.setText(response.userdata.last_name)
        edtEmail.setText(response.userdata.email)
        edtPhone.setText(response.userdata.phone_number)
        edtDob.setText(response.userdata.dob)

        if (response.userdata.gender.equals("1",ignoreCase = true))
            chkMale.isChecked=true
        else if(response.userdata.gender.equals("2",ignoreCase = true))
            chkFemale.isChecked=true
    }

    override fun onUserDetailsFailed(error_message: String) {
        mAppUtils.showErrorToast(error_message)
    }

    private fun initViewsClick(){
        rdgSex.setOnCheckedChangeListener(this)

        edtDob.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(
                    this, this, year, month, day)
            dialog.datePicker.maxDate = System.currentTimeMillis() - 1000
            dialog.show()
        }

        btnSaveChanges.setOnClickListener{
            checkValidations()
        }

        txtAddress.setOnClickListener{
            startActivity(AddressesActivity.createIntent(this))
        }
    }

    private fun checkValidations(){

        if (edtFirstName.text!!.trim().toString().isEmpty() || edtLastName.text!!.trim().toString().isEmpty() ||
                edtEmail.text!!.trim().toString().isEmpty()  || edtPhone.text!!.trim().toString().isEmpty()
                || edtDob.text!!.trim().toString().isEmpty())
        {
            mAppUtils.showToast( getString(R.string.fillallemptyfields))
        }
        else if(mGender.equals("",ignoreCase = true))
        {
            mAppUtils.showToast(getString(R.string.please_select_gender))
        }
        else if (!mAppUtils.checkEmail(edtEmail.text!!.trim().toString())) {
            mAppUtils.showToast( getString(R.string.warning_email))
        }
        else if (edtPhone.text.toString().trim().length < 10){
            mAppUtils.showToast(getString(R.string.phoneerror))
        }
        else if(mGender.equals("",ignoreCase = true))
        {
            mAppUtils.showToast(getString(R.string.please_select_gender))
        }

        else {
            val isValid = FormValidator.validate(this, SimpleErrorPopupCallback(this))
            if (isValid) {
                updateUser()
            }
        }

    }

    /*
    * updating user
    * */
    private fun updateUser(){
        if (mAppUtils.isInternetConnected) {
            mAppUtils.hideSoftKeyboard(window.decorView.rootView)
            mPresenter.updateUser(mPrefs.userId, edtFirstName.text!!.trim().toString(), edtLastName.text!!.trim().toString()
                    , edtPhone.text!!.trim().toString(), edtEmail.text!!.trim().toString()
                    ,edtDob.text.toString(),mGender)
        }
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    override fun onUserUpdated(response: RegisterResponse) {
        mAppUtils.showSuccessToast(response.success_message)
        mPrefs.name=response.user_data.first_name+" "+response.user_data.last_name
    }

    override fun onUpdationFailed(error_message: String) {
        mAppUtils.showSuccessToast(error_message)
    }

    /**
     *
     * Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.
     *
     * @param group the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            R.id.chkMale -> {mGender="1"}
            R.id.chkFemale-> {mGender="2"}
        }
    }

    /**
     * @param view the picker associated with the dialog
     * @param year the selected year
     * @param month the selected month (0-11 for compatibility with
     * [Calendar.MONTH])
     * @param dayOfMonth th selected day of the month (1-31, depending on
     * month)
     */
    override fun onDateSet(view: DatePicker?, year: Int, month1: Int, dayOfMonth: Int) {
        val month=month1+1
        mDate =  dayOfMonth.toString() + "-" + mAppUtils.getMonth(month) + "-" + year.toString()
        edtDob.setText(mDate)
    }




}