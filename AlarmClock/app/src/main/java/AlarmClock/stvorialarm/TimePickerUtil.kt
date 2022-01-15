package stvorialarm

import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import data.Alarm

object TimePickerUtil {
    fun getTimePickerHour(tp: TimePicker?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp!!.getHour()
        } else {
            tp!!.currentHour           }
    }

    fun getTimePickerMinute(tp: TimePicker?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tp!!.getMinute()
            } else {
            tp!!.currentMinute            }

    }
}