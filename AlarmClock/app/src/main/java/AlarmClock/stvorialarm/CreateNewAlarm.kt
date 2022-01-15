package stvorialarm

import AlarmClock.R
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import data.Alarm
import java.util.*
import java.util.logging.Logger.global

//fragment koji korstim za dohvacanje svih detalja za alarm
class CreateNewAlarm : Fragment() {

    var timePicker = view?.findViewById<TimePicker>(R.id.fragment_createalarm_timePicker)
    var title = view?.findViewById<TimePicker>(R.id.fragment_createalarm_title)
    var scheduleAlarm= view?.findViewById<Button>(R.id.fragment_createalarm_scheduleAlarm)
    var recurring= view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkMon)
    var mon = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkMon)
    var tue = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkTue)
    var wed = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkWed)
    var thu = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkThu)
    var fri = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkFri)
    var sat = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkSat)
    var sun = view?.findViewById<CheckBox>(R.id.fragment_createalarm_checkSun)
    var recurringOptions = view?.findViewById<LinearLayout>(R.id.fragment_createalarm_recurring_options)

    private var createAlarmViewModel: CreateAlarmViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAlarmViewModel = ViewModelProvider(this).get(
            CreateAlarmViewModel::class.java)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ponavlja li se alarm
        val view = inflater.inflate(R.layout.fragment_create_new_alarm, container, false)



        recurring?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recurringOptions?.visibility = View.VISIBLE
            } else {
                recurringOptions?.visibility = View.GONE
            }
        }


        scheduleAlarm?.setOnClickListener { v ->
            scheduleAlarm()
            findNavController(v).navigate(R.id.action_createNewAlarm_to_alarmsListFragment)
        }

        return view
    }


    private fun scheduleAlarm() {
        //datum i vrijeme alarma
        val alarmId = Random().nextInt(Int.MAX_VALUE)
        val alarm =  Alarm(
            alarmId,
            TimePickerUtil.getTimePickerHour(timePicker),
            TimePickerUtil.getTimePickerMinute(timePicker),
            title.toString(),
            System.currentTimeMillis(),
            true,
            mon!!.isChecked,
            recurring!!.isChecked,
            tue!!.isChecked,
            wed!!.isChecked,
            thu!!.isChecked,
            fri!!.isChecked,
            sat!!.isChecked,
            sun!!.isChecked
        )
        createAlarmViewModel?.insert(alarm)
        alarm.schedule(context)
    }


    }
