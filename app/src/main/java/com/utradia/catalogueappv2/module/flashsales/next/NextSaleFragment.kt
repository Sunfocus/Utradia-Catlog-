package com.utradia.catalogueappv2.module.flashsales.next

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.module.flashsales.FlashSalesActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_nextsale.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class NextSaleFragment: Fragment() ,OnNextItemClicked{


    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    private var  variableName:CountDownTimer?=null


    /*
    * View method
    * */
    override fun onItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(activity!!,id,name,false))
    }
    private  var mAdapter:NextSaleAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the view from fragmenttab1.xml
        return inflater.inflate(R.layout.fragment_nextsale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()

    }

    private fun updateUI(){
        val response= FlashSalesActivity.mFlashResponse

        if(response?.next_flash_sale!=null  && response.next_flash_sale.start_datetime!=null){
            startCountDown(response.next_flash_sale.start_datetime)
        }
        else
            clCountdown.visibility=View.GONE

        if (response?.next_flash_sale?.products!=null && response.next_flash_sale?.products!!.size>0)
        {
            val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            rvNextSale.layoutManager = layoutManager
            mAdapter = NextSaleAdapter(activity!!, response.next_flash_sale?.products!!,this)
            rvNextSale.adapter = mAdapter
        }
    }


    fun startCountDown(date: String){
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
        val eventDate = dateFormat.parse(date)
        val currentDate = Date()
        val diff =  eventDate.time-currentDate.time
        variableName=null
        variableName = object : CountDownTimer(diff, 1000){
            override fun onFinish() {
                onResume()
            }

            override fun onTick(millisUntilFinished: Long) {

                txtHours.setText(TimeUnit.MILLISECONDS.toHours(millisUntilFinished).toString())
                txtMinutes.setText(String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished))))
                txtSeconds.setText(String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))))
            }

        }

        variableName!!.start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(variableName!=null)
            variableName!!.cancel()
    }
}