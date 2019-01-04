
@file:JvmName("Constants")
@file:Suppress("PropertyName")

package com.utradia.catalogueappv2.alarmReciever

import com.utradia.catalogueappv2.BuildConfig

@JvmField val MSG_UNCOLOR_START = 0
@JvmField val MSG_UNCOLOR_STOP = 1
@JvmField val MSG_COLOR_START = 2
@JvmField val MSG_COLOR_STOP = 3

@JvmField val MESSENGER_INTENT_KEY = "${BuildConfig.APPLICATION_ID}.MESSENGER_INTENT_KEY"
@JvmField val WORK_DURATION_KEY = "${BuildConfig.APPLICATION_ID}.WORK_DURATION_KEY"
