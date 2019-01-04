package com.utradia.catalogueappv2.module.mtnmoney

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import kotlinx.android.synthetic.main.activity_mtn_payment.*
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.UnsupportedEncodingException


class MTNActivity :BaseActivity() {


    private lateinit var mItemName:String
    private var mAmount:Double=0.00
    internal var NAMESPACE = "http://services.webclient.transflow.dialect.com.gh"
    internal var METHOD_POSTINVOICE = "postInvoice"
    internal var METHOD_CHECKINVSTATUS = "checkInvStatus"
    internal var METHOD_GET_TRANS = "getTrans"
    //    String SOAP_ACTIONPostInvoice = NAMESPACE + METHOD_POSTINVOICE;
    internal var URL = "http://68.169.57.64:8080/transflow_webclient/services/InvoicingService?wsdl"
    internal var _TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"

    internal lateinit var encodeString: String
    internal lateinit var obj: SoapObject
    internal var name = "name"
    internal var info = "info"
    internal var amt = "amt"
    internal var mobile = "mobile"
    internal var mesg = "mesg"
    internal var expiry = "expiry"
    internal var billprompt = "billprompt"
    internal var thirdpartyID = "thirdpartyID"
    internal var username = "username"
    internal var password = "password"
    internal var check = 0
    internal var billStatus = ""
    internal var invoice = ""


    override val layout: Int
        get() = R.layout.activity_mtn_payment

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)

        mAppUtils.changeStatusBarColor(this)
        setToolbar()
        getIntentExtras()

        initClicks()
    }

    private fun initClicks() {
        btnPayMtn.setOnClickListener {
            makePayment()
        }
    }

    private fun makePayment() {

       // mobileMoney.setThirdPartyId("TPI44768")
       // mobileMoney.makePurchaseWithDialog(this, mItemName
         //       , "Utradia Payment Request", 3 , 0.01, "0558786744", "payment",this)

        if (edtMtnPhone.text!!.trim().toString().isEmpty())
        {
            mAppUtils.showErrorToast(getString(R.string.please_enter_phone_number))
        }
        else
            postInvoiceMethod(mItemName, edtMtnPhone.text!!.trim().toString(),"0.10")
    }

    private fun getIntentExtras() {
        mItemName=intent.getStringExtra("name_extra")
        mAmount=intent.getDoubleExtra("amount_extra",0.00)
    }

    companion object {
        fun createIntent(context: Context,itemName:String,amount:Double): Intent {
            return Intent(context, MTNActivity::class.java)
                    .putExtra("name_extra",itemName)
                    .putExtra("amount_extra",amount)
        }
    }

    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.payment)
        toolbar.setNavigationOnClickListener { finish() }
    }

    fun postInvoiceMethod(Name: String, number: String, amount: String) {
        showProgress()
        val infotxt = "information about amount"
        var data = ByteArray(0)
        try {
            data = infotxt.toByteArray(charset("UTF-8"))
            encodeString = Base64.encodeToString(data, Base64.DEFAULT)
            encodeString = encodeString.replace("\\n".toRegex(), "")
            //            String replaceString2=replaceString.replaceAll("\\+","~");
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val request = SoapObject(NAMESPACE, METHOD_POSTINVOICE)

        val _name = PropertyInfo()
        _name.setName(name)
        _name.value = Name
        _name.setType(String::class.java)
        request.addProperty(_name)

        val _info = PropertyInfo()
        _info.setName(info)
        _info.value = encodeString
        _info.setType(String::class.java)
        request.addProperty(_info)

        val _amt = PropertyInfo()
        _amt.setName(amt)
        _amt.value = amount
        _amt.setType(String::class.java)
        request.addProperty(_amt)

        val _mobile = PropertyInfo()
        _mobile.setName(mobile)
        _mobile.value = number
        _mobile.setType(String::class.java)
        request.addProperty(_mobile)

        val _mesg = PropertyInfo()
        _mesg.setName(mesg)
        _mesg.value = "Payment request from utradia"
        _mesg.setType(String::class.java)
        request.addProperty(_mesg)

        val _exp = PropertyInfo()
        _exp.setName(expiry)
        _exp.value = "2020-10-10"
        _exp.setType(String::class.java)
        request.addProperty(_exp)

        val _bill = PropertyInfo()
        _bill.setName(billprompt)
        _bill.value = "2"
        _bill.setType(String::class.java)
        request.addProperty(_bill)

        val _thirdpartyID = PropertyInfo()
        _thirdpartyID.setName(thirdpartyID)
        _thirdpartyID.value = "TPI44768"
        _thirdpartyID.setType(String::class.java)
        request.addProperty(_thirdpartyID)

        val _user = PropertyInfo()
        _user.setName(username)
        _user.value = "UTRAD123"
        _user.setType(String::class.java)
        request.addProperty(_user)

        val _pass = PropertyInfo()
        _pass.setName(password)
        _pass.value = "7b0fprlm"
        _pass.setType(String::class.java)
        request.addProperty(_pass)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(request)
        envelope.dotNet = true

        val androidHttpTransport = HttpTransportSE(URL)

        try {
            androidHttpTransport.setXmlVersionTag(_TAG)
            androidHttpTransport.call(NAMESPACE + METHOD_POSTINVOICE, envelope)
            val response = envelope.bodyIn as SoapObject

            obj = envelope.response as SoapObject
            Log.e("resonse", obj.toString())
            val code = obj.getProperty("responseCode").toString()
            val msg = obj.getProperty("responseMessage").toString()
            invoice = obj.getProperty("invoiceNo").toString()
            if (code == "0000") {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                val handler = Handler()
                handler.postDelayed({
                    check++
                    Log.e("check_staus", check.toString())
                    checkInvStatusSend(invoice)
                }, 60000)
            } else {
               hideProgress()
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                //                resMsg.setText("Msg:" + msg);
                finish()
            }

            Log.e("RES", obj.toString())
        } catch (e: Exception) {
           hideProgress()
            Log.e("ERR", e.toString())
        }

    }


    private fun checkInvStatusSend(invoice: String) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val request = SoapObject(NAMESPACE, METHOD_CHECKINVSTATUS)

        val _invoice = PropertyInfo()
        _invoice.setName("invoiceNo")
        _invoice.value = invoice
        _invoice.setType(String::class.java)
        request.addProperty(_invoice)

        val _user = PropertyInfo()
        _user.setName(username)
        _user.value = "UTRAD123"
        _user.setType(String::class.java)
        request.addProperty(_user)

        val _pass = PropertyInfo()
        _pass.setName(password)
        _pass.value = "7b0fprlm"
        _pass.setType(String::class.java)
        request.addProperty(_pass)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(request)
        envelope.dotNet = true

        val androidHttpTransport = HttpTransportSE(URL)

        try {
            androidHttpTransport.setXmlVersionTag(_TAG)
            androidHttpTransport.call(NAMESPACE + METHOD_CHECKINVSTATUS, envelope)
            val response = envelope.bodyIn as SoapObject
            obj = envelope.response as SoapObject
            Log.e("response_invoice", obj.toString())
            val code = obj.getProperty("responseCode").toString()
            val msg = obj.getProperty("responseMessage").toString()

            if (code == "0000") {
                //     progressDialog.dismiss();
                //  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                billStatus = msg
                Log.e("message", mesg)
                //order_create(sharedPreferences.getString(MyAppController.USER_ID, ""), getTotalPrice(), invoice)

                //                txtcheckInvoiceMsg.setText(msg);
            } else {
                /* if (check < 2) {
                    billStatus = msg;
                    Log.e("message", mesg);
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            check++;
                            checkInvStatusSend(invoice);
                            if (check == 1) {
                                progressDialog.dismiss();
                                finish();
//                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            }
                        }
                    }, 60000);
                } else {*/
                billStatus = msg
                Log.e("message", mesg)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
               hideProgress()
                finish()
                //                txtcheckInvoiceMsg.setText(msg);
                //   }
            }
            Log.e("InvoiceRES", obj.toString())
        } catch (e: HttpResponseException) {
            e.printStackTrace()
        } catch (soapFault: SoapFault) {
            soapFault.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}