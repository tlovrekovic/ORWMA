package stvorialarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import data.Alarm
import data.AlarmRepository
//bitna klasa da ne izgubimo alarme koji su stvoreni nakon ponovnog boota
//poziva sinsert metoda na AlarmTepository i dodaje se alarm
class CreateAlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository?
    fun insert(alarm: Alarm?) {
        alarmRepository!!.insert(alarm)
    }

    init {
        alarmRepository = AlarmRepository(application)
    }
}