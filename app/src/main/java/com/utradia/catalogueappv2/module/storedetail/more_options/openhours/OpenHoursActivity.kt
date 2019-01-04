package com.utradia.catalogueappv2.module.storedetail.more_options.openhours

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.OpenHours
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity.Companion.response
import kotlinx.android.synthetic.main.activity_openhours.*


class OpenHoursActivity: BaseActivity() {
    override val layout: Int
        get() = R.layout.activity_openhours

    override fun showToolbar(): Boolean {
       return true
    }

    override val views: List<View>?
        get() = null

    private val mOpenHours:MutableList<OpenHours> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mAppUtils.changeStatusBarColor(this)


        updateUI()
        setToolbar()
    }


    private fun setToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back)

        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setTitle(R.string.open_hours)

        toolbar.setNavigationOnClickListener { finish() }
    }





    private fun updateUI(){
        if (response!!.locations.size>0)
        updateHourList()

        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rvOpenHours.layoutManager = layoutManager1

        val mAdapter = OpenHoursAdapter(this,mOpenHours)
        rvOpenHours.adapter = mAdapter
    }

    private fun updateHourList(){
        /*Current Day Name*/
        mOpenHours.clear()
        // val dayName=mDateTimeUtils.dayName
        val response= StoreDetailActivity.response
        /*
        * monday
        * */
        val bean1=OpenHours()
        bean1.timing=response!!.locations[0].mon_time
        bean1.day= getString(R.string.monday)
        if (response.locations[0].mon_time.equals("Closed",true))
            bean1.status= "0"
        else
            bean1.status= "1"

        mOpenHours.add(bean1)
        /*
        * tuesday
        * */
        val bean2=OpenHours()
        bean2.timing=response.locations[0].tue_time
        bean2.day= getString(R.string.tuesday)
        if (response.locations[0].tue_time.equals("Closed",true))
            bean2.status= "0"
        else
            bean2.status= "1"
        mOpenHours.add(bean2)

        /*
        * wednesday
        * */
        val bean3=OpenHours()
        bean3.timing=response.locations[0].wed_time
        bean3.day= getString(R.string.wednesday)
        if (response.locations[0].wed_time.equals("Closed",true))
            bean3.status= "0"
        else
            bean3.status= "1"

        mOpenHours.add(bean3)

        /*
        * thursday
        * */
        val bean4=OpenHours()
        bean4.timing=response.locations[0].thu_time
        bean4.day= getString(R.string.thursday)
        if (response.locations[0].thu_time.equals("Closed",true))
            bean4.status= "0"
        else
            bean4.status= "1"

        mOpenHours.add(bean4)

        /*
        * Friday
        * */
        val bean5=OpenHours()
        bean5.timing=response.locations[0].fri_time
        bean5.day= getString(R.string.friday)
        if (response.locations[0].fri_time.equals("Closed",true))
            bean5.status= "0"
        else
            bean5.status= "1"

        mOpenHours.add(bean5)

        /*
        * Saturday
        * */
        val bean6=OpenHours()
        bean6.timing=response.locations[0].sat_time
        bean6.day= getString(R.string.saturday)
        if (response.locations[0].sat_time.equals("Closed",true))
            bean6.status= "0"
        else
            bean6.status= "1"

        mOpenHours.add(bean6)

        /*
       * Sunday
       * */
        val bean7=OpenHours()
        bean7.timing=response.locations[0].sun_time
        bean7.day= getString(R.string.sunday)
        if (response.locations[0].sun_time.equals("Closed",true))
            bean7.status= "0"
        else
            bean7.status= "1"

        mOpenHours.add(bean7)
    }

}