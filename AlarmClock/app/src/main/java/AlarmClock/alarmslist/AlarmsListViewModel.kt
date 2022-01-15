package alarmslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import data.Alarm
import data.AlarmRepository

class AlarmsListViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository?
    private val alarmsLiveData: LiveData<MutableList<Alarm?>?>?
    fun update(alarm: Alarm?) {
        alarmRepository!!.update(alarm)
    }

    fun getAlarmsLiveData(): LiveData<MutableList<Alarm?>?>? {
        return alarmsLiveData
    }

    init {
        alarmRepository = AlarmRepository(application)
        alarmsLiveData = alarmRepository.getAlarmsLiveData()
    }
}