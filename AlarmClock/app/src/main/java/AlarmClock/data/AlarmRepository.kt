package data

import android.app.Application
import androidx.lifecycle.LiveData

class AlarmRepository(application: Application?) {
    private val alarmDao: AlarmDao?
    private val alarmsLiveData: LiveData<MutableList<Alarm?>?>?
    fun insert(alarm: Alarm?) {
        AlarmDatabase.Companion.databaseWriteExecutor.execute(Runnable { alarmDao!!.insert(alarm) })
    }

    fun update(alarm: Alarm?) {
        AlarmDatabase.Companion.databaseWriteExecutor.execute(Runnable { alarmDao!!.update(alarm) })
    }

    fun getAlarmsLiveData(): LiveData<MutableList<Alarm?>?>? {
        return alarmsLiveData
    }

    init {
        val db: AlarmDatabase? = AlarmDatabase.Companion.getDatabase(application)
        alarmDao = db?.alarmDao()
        alarmsLiveData = alarmDao!!.getAlarms()
    }
}