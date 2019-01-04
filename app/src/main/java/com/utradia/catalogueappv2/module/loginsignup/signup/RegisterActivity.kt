package com.utradia.catalogueappv2.module.loginsignup.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import butterknife.BindView
import com.google.firebase.iid.FirebaseInstanceId
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.annotation.Password
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import eu.inmite.android.lib.validations.form.FormValidator
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.view_activation.*
class RegisterActivity:BaseActivity(),RegisterView, Validator.ValidationListener,RadioGroup.OnCheckedChangeListener{

    override fun showToolbar(): Boolean {
        return false
    }

    override fun onUserRegistered(registerResponse: RegisterResponse) {

        mPrefs.name = registerResponse.user_data.first_name +" "+ registerResponse.user_data.last_name
        mPrefs.setUserID(registerResponse.user_data.id)
        mPrefs.isUserLoggedIn=true

        val dialog = mDialogsUtil.showDialog(this, R.layout.view_activation)
        dialog.show()

        dialog.txtOk.setOnClickListener {
            dialog.dismiss()
            passUser()
        }
        dialog.txtMessage.text=registerResponse.success_message

    }

    override fun onUserRegisterationFailed(registerResponse: RegisterResponse) {
    }

    override fun enableButton() {
        btnRegister.isEnabled=true
        btnRegister.alpha=1f
    }

    override fun disableButton() {

        btnRegister.isEnabled=false
        btnRegister.alpha=.5f
    }

    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    override
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mDialogsUtil: DialogsUtil
    @Inject
    lateinit var mRegisterPresenter: RegisterPresenter
    private var flag:Int=0


    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    lateinit var edtPassword: EditText


    private var validator: Validator? = null
    private var mGender: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        /*attaching view{@link LoginView} to our presenter*/
        mRegisterPresenter.injectDependencies(this)
        mRegisterPresenter.attachView(this)

        /*get Intent*/
        flag=intent.getIntExtra("flag_extra",0)
        /*initializing clicks*/
        initClicks()
        validator = Validator(this)
        validator!!.setValidationListener(this)

    }

    override val layout: Int
        get() = R.layout.activity_register

    override val views: List<View>?
        get() = null

    companion object {
        fun createIntent(context: Context,flag:Int):Intent{
            return Intent(context,RegisterActivity::class.java).putExtra("flag_extra",flag)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mRegisterPresenter.cancelAllAsync()
        mRegisterPresenter.detachView()
    }
    private fun initClicks(){

        edtPassword= findViewById(R.id.edtPassword)

        rdgSex.setOnCheckedChangeListener(this)
        /*on register button click*/
        btnRegister.setOnClickListener{
            if (edtFirstName.text!!.trim().toString().isEmpty() || edtLastName.text!!.trim().toString().isEmpty() ||
                edtEmail.text!!.trim().toString().isEmpty() || edtPassword.text.trim().toString().isEmpty() ||
                edtRetypePassword.text!!.trim().toString().isEmpty() || edtPhone.text!!.trim().toString().isEmpty())
            {
            mAppUtils.showToast( getString(R.string.fillallemptyfields))
            }
            else{
                mAppUtils.hideSoftKeyboard(window.decorView.rootView)
                validator?.validate()
            }
        }

    }

    override fun onValidationSucceeded() {
        mAppUtils.hideSoftKeyboard(window.decorView.rootView)

        if (!mAppUtils.checkEmail(edtEmail.text!!.trim().toString())) {
            mAppUtils.showToast( getString(R.string.warning_email))
        }
        else if (edtPhone.text.toString().trim().length < 10){
            mAppUtils.showToast(getString(R.string.phoneerror))
        }
        else
        if (!edtPassword.text.toString().trim().equals(edtRetypePassword.text.toString().trim(), ignoreCase = false)) {
            mAppUtils.showToast(   getString(R.string.passworddoesnotmatch))
        }
        else if(mGender.equals("",ignoreCase = true))
        {
            mAppUtils.showToast(getString(R.string.please_select_gender))
        }
        else {
            val isValid = FormValidator.validate(this@RegisterActivity, SimpleErrorPopupCallback(this@RegisterActivity))
            if (isValid) {
                registerUser()
            }
        }

    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view = error.view
            val message = error.getCollatedErrorMessage(this)
            mAppUtils.showErrorToast(message)
            // Display error messages ;)
            if (view is EditText) {
                (view).error = resources.getString(R.string.password_valid)
            }
            //   mAppUtils.showErrorToast(message);

        }
    }

    /*register user*/
    private fun registerUser(){
        if (mAppUtils.isInternetConnected) {
            mAppUtils.hideSoftKeyboard(window.decorView.rootView)
            disableButton()
            mRegisterPresenter.signUpUser("manual", edtFirstName.text!!.trim().toString(), edtLastName.text!!.trim().toString()
                    , edtPhone.text!!.trim().toString(), edtEmail.text!!.trim().toString(), edtPassword.text.trim().toString()
                    ,"android", FirebaseInstanceId.getInstance().token!! )
        }
        else {
            enableButton()
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
        }
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


    /*
    pass user after validation
    * */
    private fun passUser(){
        if (flag==ValueConstants.DEFAULT)
        {
            startActivity(HomeActivity.createIntent(this))
            finishAffinity()
        }
        else if (flag==ValueConstants.PRODUCT_DETAIL)
            startActivity(ProductDetailActivity.createIntent(this))

        else if(flag==ValueConstants.HOME_SCREEN)
            startActivity(HomeActivity.createIntent(this))
    }
}