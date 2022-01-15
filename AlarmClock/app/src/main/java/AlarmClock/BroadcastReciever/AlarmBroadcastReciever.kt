package BroadcastReciever

import Service.AlarmService
import Service.RescheduleAlarmsService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import java.util.*
//koristimo Broadcast stvoren od strane klase Alarm . Kad ova klasa primi Broadcast koriti ga da započne alarm koji će stvoriti notifikaciju,vibracije i zvuk
class AlarmBroadcastReceiver : BroadcastReceiver() {
    //provjeravamo da primljen broadcast nije broadcast koji je poslan kad se uređaj pali
    //ako to nije slučaj dohvaćamo alarm
    //ako se alarm ponavlja provjeravam dali je danas taj dan
    //ako se uoči ACTION_BOOT_COPMPLETED opet dohvaćam alarm iz alarm databaze
    override fun onReceive(context: Context?, intent: Intent?) =
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        } else {
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if (!intent?.getBooleanExtra(RECURRING, false)!!) {
                startAlarmService(context, intent)
            }
            run {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent)
                }
            }
        }

    private fun alarmIsToday(intent: Intent?): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar[Calendar.DAY_OF_WEEK]
        when (today) {
            Calendar.MONDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(MONDAY, false)
                }
            }
            Calendar.TUESDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(TUESDAY, false)
                }
            }
            Calendar.WEDNESDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(WEDNESDAY, false)
                }
            }
            Calendar.THURSDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(THURSDAY, false)
                }
            }
            Calendar.FRIDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(FRIDAY, false)
                }
            }
            Calendar.SATURDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(SATURDAY, false)
                }
            }
            Calendar.SUNDAY -> {
                if (intent != null) {
                    return intent.getBooleanExtra(SUNDAY, false)
                }
            }
        }
        return false
    }

    private fun startAlarmService(context: Context?, intent: Intent?) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(TITLE, intent?.getStringExtra(TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intentService)
        } else {
            context?.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context?) {
        val intentService = Intent(context, RescheduleAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intentService)
        } else {
            context?.startService(intentService)
        }
    }

    companion object {
        val MONDAY: String? = "MONDAY"
        val TUESDAY: String? = "TUESDAY"
        val WEDNESDAY: String? = "WEDNESDAY"
        val THURSDAY: String? = "THURSDAY"
        val FRIDAY: String? = "FRIDAY"
        val SATURDAY: String? = "SATURDAY"
        val SUNDAY: String? = "SUNDAY"
        val RECURRING: String? = "RECURRING"
        val TITLE: String? = "TITLE"
    }
}