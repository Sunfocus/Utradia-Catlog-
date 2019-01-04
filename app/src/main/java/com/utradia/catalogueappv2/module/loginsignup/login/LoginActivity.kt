package com.utradia.catalogueappv2.module.loginsignup.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.facebook.*
import com.utradia.catalogueappv2.R
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.iid.FirebaseInstanceId
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.LoginResponse
import com.utradia.catalogueappv2.model.RegisterResponse
import com.utradia.catalogueappv2.module.home.HomeActivity
import com.utradia.catalogueappv2.module.loginsignup.signup.RegisterActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.utils.AppUtils
import com.utradia.catalogueappv2.utils.DialogsUtil
import com.utradia.catalogueappv2.utils.PreferenceManager
import eu.inmite.android.lib.validations.form.FormValidator
import eu.inmite.android.lib.validations.form.annotations.MinLength
import eu.inmite.android.lib.validations.form.annotations.NotEmpty
import eu.inmite.android.lib.validations.form.annotations.RegExp
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_update_email.*
import org.json.JSONException
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginView , FacebookCallback<LoginResult>, GoogleApiClient.OnConnectionFailedListener {

    override fun showToolbar(): Boolean {
        return false
    }
    override fun onUserLoggedIn(response: LoginResponse) {
        mPrefs.setUserID(response.user_data.id)
        mPrefs.cartCount = response.cart_count.toString()
        mPrefs.name = response.user_data.first_name +" "+ response.user_data.last_name
        mPrefs.email = response.user_data.email
        mPrefs.profilePic=response.user_data.image
        passUser()
    }

    override fun onLoginFailed(response: LoginResponse) {
        mAppUtils.showSnackBar(window.decorView.rootView,response.error_message)
    }


    override fun enableButton() {
        btnSignin.isEnabled = true
        btnSignin.alpha = 1f
    }

    override fun disableButton() {
        btnSignin.isEnabled = false
        btnSignin.alpha = 0.5f
    }

    override fun onSocialUserAlreadyExist(response: RegisterResponse) {

       /* mPrefs.isUserLoggedIn=true*/
        mPrefs.setUserID(response.user_data.id)
        //mPrefs.cartCount = response.cart_count.toString()
        mPrefs.name = response.user_data.first_name +" "+ response.user_data.last_name
        mPrefs.email = response.user_data.email
        mPrefs.profilePic=response.user_data.image
        /*
        * check if email is updated or not*/
        checkUserData(response)
       /* startActivity(HomeActivity.createIntent(this))
        finishAffinity()*/

    }

    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mDialogsUtil:DialogsUtil
    @Inject
    override
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mLoginPresenter: LoginPresenter

    lateinit  var mInternetWarning :String

    @NotEmpty(messageId = R.string.warning_field_empty, order = 1)
    @RegExp(value = RegExp.EMAIL, messageId = R.string.warning_email, order = 2)
    lateinit var edtEmail: EditText
    @MinLength(value = 8, messageId = R.string.warning_password_length, order = 2)


    private lateinit var callbackManager: CallbackManager
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var flag:Int=0
    private var mHideUnhide:Int=0

    override val layout: Int
        get() = R.layout.activity_login

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)
        /*attaching view{@link LoginView} to our presenter*/
        mLoginPresenter.injectDependencies(this)
        mLoginPresenter.attachView(this)
        /*fb initials*/
        callbackManager = CallbackManager.Factory.create()
        login_button.registerCallback(callbackManager, this)
        login_button.setReadPermissions("email", "public_profile")
        LoginManager.getInstance().logOut()

        /*get Intent*/
        flag=intent.getIntExtra("flag_extra",0)
        mHideUnhide=intent.getIntExtra("hide_extra",0)
        updateSkipButton()


        mInternetWarning=getString(R.string.toast_network_not_available)

        /*init clicks*/
        initClicks()
        /*google plus initials*/
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

    }

    /*Skip button visibility*/
    private fun updateSkipButton() {
        if (mHideUnhide==ValueConstants.HIDE_SKIP)
            txtSkip.visibility=View.GONE
        else if(mHideUnhide==ValueConstants.UNHIDE_SKIP){
            txtSkip.visibility=View.VISIBLE
        }
    }

    private fun initClicks(){

        edtEmail = findViewById(R.id.edtEmail)

        /*Sign in button*/
        btnSignin.setOnClickListener {
            val isValid = FormValidator.validate(this@LoginActivity, SimpleErrorPopupCallback(this@LoginActivity))
            if (isValid) {
                //call to login user function in presenter class
                try {

                    if (mAppUtils.isInternetConnected) {
                        mAppUtils.hideSoftKeyboard(window.decorView.rootView)
                        disableButton()
                        mLoginPresenter.loginUser(edtEmail.text.trim().toString(), edtPassword.text!!.trim().toString()
                                , FirebaseInstanceId.getInstance().token!!,"android", "manual")
                    } else {
                        enableButton()
                        mAppUtils.showSnackBar(window.decorView.rootView, mInternetWarning)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        /*Sign up button*/
        txtNewUser.setOnClickListener {  startActivity(RegisterActivity.createIntent(this,flag)) }
        /*Facebook login button*/
        imgFacebook.setOnClickListener {  login_button.performClick() }
        /*Google login button*/
        imgGoogle.setOnClickListener { signIn() }

        /*Skip button */
        txtSkip.setOnClickListener {
            startActivity(HomeActivity.createIntent(this))
            finishAffinity()
        }

        /*forgot password link*/
        txtForgotPassword.setOnClickListener {
            val dialog = mDialogsUtil.showDialog(this, R.layout.view_update_email)
            dialog.show()

            dialog.txtUpdate.text=getString(R.string.submit)
            dialog.txtMessage.text=getString(R.string.please_enter_your_email_id)
        }

    }

    /**
     * un subscribe the subscriber
     */
    override fun onDestroy() {
        super.onDestroy()
        mLoginPresenter.cancelAllAsync()
        mLoginPresenter.detachView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        fun createIntent(context: Context,flag: Int,hide_skip: Int): Intent {
            return Intent(context, LoginActivity::class.java).putExtra("flag_extra",flag).putExtra("hide_extra",hide_skip)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == ValueConstants.REQUEST_FBLOGIN) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        } else if (resultCode == RESULT_OK && requestCode == ValueConstants.G_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    /*Facebook callbacks  .........................................................*/
    override fun onSuccess(loginResult: LoginResult) {
        val request = GraphRequest.newMeRequest(
                loginResult.accessToken
        ) { `object`, response ->
            // Application code
            try {
                var email = ""
                if (`object`.has("email"))
                    email = `object`.getString("email")

                val firstname = `object`.getString("first_name")
                val lastname = `object`.getString("last_name")
                val id = `object`.getString("id")

                val profile_pic = "http://graph.facebook.com/$id/picture?type=large"
                mPrefs.setProfilePic(profile_pic)

                loginSocially(email, id, "$firstname $lastname", "", "fb")
                LoginManager.getInstance().logOut()
            } catch (e: JSONException) {
                e.printStackTrace()

            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,first_name,last_name,email")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun onCancel() {

    }

    override fun onError(error: FacebookException) {

    }


    /*google plus >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, ValueConstants.G_SIGN_IN)
    }


    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    private fun handleSignInResult(result: GoogleSignInResult) {

        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            if (acct != null) {
                var personName: String? = ""
                if (acct.displayName != null) {
                    personName = acct.displayName
                }
                if (acct.photoUrl != null) {
                    val personPhotoUrl = acct.photoUrl!!.toString()
                    mPrefs.setProfilePic(personPhotoUrl)
                }
                val email = acct.email
                val userId = acct.id


                //email: String, fb_id: String  ,name: String, google_id: String,loginType : String
                loginSocially(email!!, "",   personName!!,userId!!,"google")
            }
        }
    }


    /*
    sign up user socially
    */
    private fun loginSocially(email: String, fb_id: String  ,name: String, google_id: String,loginType : String) {

        if (mAppUtils.isInternetConnected) {
            mAppUtils.hideSoftKeyboard(window.decorView.rootView)
            disableButton()
            mLoginPresenter.loginUserSocially(loginType, fb_id,google_id,email,name
                    , FirebaseInstanceId.getInstance().token!!,"android")
        } else {
            enableButton()
            mAppUtils.showSnackBar(window.decorView.rootView, mInternetWarning)
        }
    }


    /*Check User data for email */
    private fun checkUserData(response: RegisterResponse){
        if(response.user_data.email.equals("",true))
        {
            /*update Email*/
            updateEmail(response.user_data.id)
        }
        else
        {
            passUser()
        }
    }

    private fun updateEmail(user_id: String){

        val dialog = mDialogsUtil.showDialog(this, R.layout.view_update_email)
        dialog.show()

        dialog.txtUpdate.setOnClickListener {

            if (!mAppUtils.checkEmail(dialog.edtEmailUpdate.text!!.trim().toString())) {
                mAppUtils.showToast( getString(R.string.warning_email))
            }
            else{
                if (mAppUtils.isInternetConnected) {
                    mAppUtils.hideSoftKeyboard(window.decorView.rootView)
                    disableButton()
                    dialog.dismiss()
                    mLoginPresenter.updateEmail(dialog.edtEmailUpdate.text.toString() ,user_id)
                } else {
                    enableButton()
                    mAppUtils.showSnackBar(window.decorView.rootView, mInternetWarning)
                }
            }
        }

    }

    private fun passUser(){
        mPrefs.isUserLoggedIn = true
        if (flag==ValueConstants.DEFAULT){
            startActivity(HomeActivity.createIntent(this))
            finishAffinity()
        }
        else if(flag==ValueConstants.PRODUCT_DETAIL)
            startActivity(ProductDetailActivity.createIntent(this))
        else if(flag==ValueConstants.PRODUCT_LIST)
            startActivity(ProductListActivity.createIntent(this))

        else if(flag==ValueConstants.HOME_SCREEN)
            startActivity(HomeActivity.createIntent(this))

    }
}
