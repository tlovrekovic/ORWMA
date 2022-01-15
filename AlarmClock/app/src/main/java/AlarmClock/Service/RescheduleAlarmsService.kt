package Service

import android.app.Service.START_STICKY
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import data.AlarmRepository

class RescheduleAlarmsService : LifecycleService() {
    override fun onCreate() {
        super.onCreate()
    }
    //onStartCommand koristi AlarmRepository da vrati sve Alarme
    //isStarted provjerava sanje alarma
    //ako je alarm pokrenut .schedule ga ponovno postavlja

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val alarmRepository = AlarmRepository(application)
        alarmRepository.getAlarmsLiveData()!!.observe(this, { alarms ->
            for (a in alarms!!) {
                if (a!!.isStarted()) {
                    a.schedule(applicationContext)
                }
            }
        })
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}