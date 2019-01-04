package com.utradia.catalogueappv2.test

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Messenger
import android.os.PersistableBundle
import android.util.Log
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.alarmReciever.IncomingMessageHandler
import com.utradia.catalogueappv2.alarmReciever.JobService
import com.utradia.catalogueappv2.alarmReciever.MESSENGER_INTENT_KEY
import com.utradia.catalogueappv2.alarmReciever.WORK_DURATION_KEY
import kotlinx.android.synthetic.main.activity_test.*
import java.util.concurrent.TimeUnit

class TestActivity : AppCompatActivity() {

    lateinit private var serviceComponent: ComponentName
    lateinit private var handler: IncomingMessageHandler

    private var jobId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)



        handler = IncomingMessageHandler(this)
        serviceComponent = ComponentName(this, JobService::class.java)

        btn_start.setOnClickListener {

            startJob()
        }
        btn_cancel.setOnClickListener {
            finishJob()
        }
    }

    override fun onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(Intent(this, JobService::class.java))
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        // Start service and provide it a way to communicate with this class.
        val startServiceIntent = Intent(this, JobService::class.java)
        val messengerIncoming = Messenger(handler)
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming)
        startService(startServiceIntent)
    }

    private fun startJob()
    {
        val builder = JobInfo.Builder(jobId++, serviceComponent)


        val delay = "5"
        if (delay.isNotEmpty()) {
            builder.setMinimumLatency(delay.toLong() * TimeUnit.SECONDS.toMillis(1))
        }

        val deadline = "5"
        if (deadline.isNotEmpty()) {
            builder.setOverrideDeadline(deadline.toLong() * TimeUnit.SECONDS.toMillis(1))
        }



        // Extras, work duration.
        val extras = PersistableBundle()
        var workDuration = "5"
        if (workDuration.isEmpty()) workDuration = "1"
        extras.putLong(WORK_DURATION_KEY, workDuration.toLong() * TimeUnit.SECONDS.toMillis(1))

        // Finish configuring the builder
        (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(builder.build())
    }

    private fun finishJob()
    {

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val allPendingJobs = jobScheduler.allPendingJobs


        Log.e("pending task",""+allPendingJobs.size)


        (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).cancelAll()

        Log.e("job scheduler","cancel all")

    }
}
