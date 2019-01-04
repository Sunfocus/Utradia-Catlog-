

package com.utradia.catalogueappv2.alarmReciever

import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.test.TestActivity
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * A [Handler] allows you to send messages associated with a thread. A [Messenger]
 * uses this handler to communicate from [MyJobService]. It's also used to make
 * the start and stop views blink for a short period of time.
 */
internal class IncomingMessageHandler(activity: TestActivity) : Handler() {

    // Prevent possible leaks with a weak reference.
    private val mainActivity: WeakReference<TestActivity> = WeakReference(activity)

    override fun handleMessage(msg: Message) {
        val mainActivity = mainActivity.get() ?: return

        when (msg.what) {
            /*
             * Receives callback from the service when a job has landed
             * on the app. Turns on indicator and sends a message to turn it off after
             * a second.
             */
            MSG_COLOR_START -> {
                // Start received, turn on the indicator and show text.


                Log.e("asas","asasassa")

                updateParamsTextView(msg.obj, "started")
                sendMessageDelayed(Message.obtain(this, MSG_UNCOLOR_START),
                        TimeUnit.SECONDS.toMillis(1))
            }
            /*
             * Receives callback from the service when a job that previously landed on the
             * app must stop executing. Turns on indicator and sends a message to turn it
             * off after two seconds.
             */
            MSG_COLOR_STOP -> {
                // Stop received, turn on the indicator and show text.
                updateParamsTextView(msg.obj, "stopped")
                sendMessageDelayed(obtainMessage(MSG_UNCOLOR_STOP), TimeUnit.SECONDS.toMillis(1))
            }
            MSG_UNCOLOR_START -> {
                uncolorButtonAndClearText( mainActivity)
            }
            MSG_UNCOLOR_STOP -> {
                uncolorButtonAndClearText(mainActivity)
            }
        }
    }

    private fun uncolorButtonAndClearText(activity: TestActivity) {
        updateParamsTextView()
    }

    private fun updateParamsTextView(jobId: Any? = null, action: String = "") {
        val mainActivity = mainActivity.get() ?: return

        if (jobId == null) {
            return
        }

    }
}
