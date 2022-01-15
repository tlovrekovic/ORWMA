package data

import BroadcastReciever.AlarmBroadcastReceiver
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import stvorialarm.DanUtil
import java.lang.Exception
import java.util.*
//okidač za slanje broadcasta jednom kad je alarm aktivan
//koristim PendingIntent s AlarmManagerom da pošalju akciju za pokretanje alarma koja će biti obrađena BroadcastRecieverom
@Entity(tableName = "alarm_table")
class Alarm(
    @field:PrimaryKey private var alarmId: Int,
    private val hour: Int,
    private val minute: Int,
    private val title: String?,
    private var created: Long,
    private var started: Boolean,
    private val recurring: Boolean,
    private val monday: Boolean,
    private val tuesday: Boolean,
    private val wednesday: Boolean,
    private val thursday: Boolean,
    private val friday: Boolean,
    private val saturday: Boolean,
    private val sunday: Boolean
) {
    fun getHour(): Int {
        return hour
    }

    fun getMinute(): Int {
        return minute
    }

    fun isStarted(): Boolean {
        return started
    }

    fun getAlarmId(): Int {
        return alarmId
    }

    fun setAlarmId(alarmId: Int) {
        this.alarmId = alarmId
    }

    fun isRecurring(): Boolean {
        return recurring
    }

    fun isMonday(): Boolean {
        return monday
    }

    fun isTuesday(): Boolean {
        return tuesday
    }

    fun isWednesday(): Boolean {
        return wednesday
    }

    fun isThursday(): Boolean {
        return thursday
    }

    fun isFriday(): Boolean {
        return friday
    }

    fun isSaturday(): Boolean {
        return saturday
    }

    fun isSunday(): Boolean {
        return sunday
    }

    fun schedule(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.Companion.RECURRING, recurring)
        intent.putExtra(AlarmBroadcastReceiver.Companion.MONDAY, monday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.TUESDAY, tuesday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.WEDNESDAY, wednesday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.THURSDAY, thursday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.FRIDAY, friday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.SATURDAY, saturday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.SUNDAY, sunday)
        intent.putExtra(AlarmBroadcastReceiver.Companion.TITLE, title)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        // ako je vrijeme za alarm proslo,povecaj dan za 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }
        if (!recurring) {
            var toastText: String? = null
            try {
                toastText = String.format(
                    "One Time Alarm %s scheduled for %s at %02d:%02d", title, DanUtil.toDay(
                        calendar[Calendar.DAY_OF_WEEK]
                    ), hour, minute, alarmId
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val toastText = String.format(
                "Recurring Alarm %s scheduled for %s at %02d:%02d",
                title,
                getRecurringDaysText(),
                hour,
                minute,
                alarmId
            )
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }
        started = true
    }
    //slicno kao i zakazati alarm
    //prvo uzimamo referancu na Alarm pozivajuci getSystemService(Context.ALARM_SERVICE)
    //zatim stvaramo Intent koji koristi AlarmBroadcastReceiver i koristimo taj Intent da stvorimo PendingIntent koji ima referencu na alarm id
    //zatim pozivamo calel(PendingIntent) koji gasi alarm
    fun cancelAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        started = false
        val toastText =
            String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    fun getRecurringDaysText(): String? {
        if (!recurring) {
            return null
        }
        var days = ""
        if (monday) {
            days += "Mo "
        }
        if (tuesday) {
            days += "Tu "
        }
        if (wednesday) {
            days += "We "
        }
        if (thursday) {
            days += "Th "
        }
        if (friday) {
            days += "Fr "
        }
        if (saturday) {
            days += "Sa "
        }
        if (sunday) {
            days += "Su "
        }
        return days
    }

    fun getTitle(): String? {
        return title
    }

    fun getCreated(): Long {
        return created
    }

    fun setCreated(created: Long) {
        this.created = created
    }
}