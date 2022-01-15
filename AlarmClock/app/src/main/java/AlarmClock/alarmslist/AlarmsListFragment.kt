package alarmslist

import AlarmClock.R
import AlarmClock.alarmslist.AlarmRecyclerViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.Alarm
//prikazuje sve alarme u RecyclerViewu koji takoder sadrzi detalje o vremenu,naslovu itd..,ima i prekidac za gasenje
//dohvaca alarme pomocu AlarmsListViewModela koji koristi AlarmRepository za lociranje alarma
//observer kad dohvadamo Alarms da bi se RecyclerView automatski promjenio ako ima kakvih promjena
//alarmslistfragment takoder navigira korisnika do CreateAlarmFragment ako kliknemo na gumb za dodavanje alarma
class AlarmsListFragment : Fragment(), OnToggleAlarmListener {
    private var alarmRecyclerViewAdapter: AlarmRecyclerViewAdapter? = null
    private var alarmsListViewModel: AlarmsListViewModel? = null
    private var alarmsRecyclerView: RecyclerView? = null
    private var addAlarm: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmRecyclerViewAdapter = AlarmRecyclerViewAdapter(this)
        alarmsListViewModel = ViewModelProvider(this).get(AlarmsListViewModel::class.java)
        alarmsListViewModel!!.getAlarmsLiveData()?.observe(this, { alarms ->
            if (alarms != null) {
                alarmRecyclerViewAdapter!!.setAlarms(alarms)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_listalarms, container, false)
        alarmsRecyclerView = view.findViewById(R.id.fragment_listalarms_recylerView)
        alarmsRecyclerView!!.layoutManager = LinearLayoutManager(context)
        alarmsRecyclerView!!.adapter = alarmRecyclerViewAdapter
        //gumb
        addAlarm = view.findViewById(R.id.fragment_listalarms_addAlarm)
        addAlarm?.setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_alarmsListFragment_to_createNewAlarm)
        }
        return view
    }

    override fun onToggle(alarm: Alarm?) {
        if (alarm!!.isStarted()) {
            alarm.cancelAlarm(context)
            alarmsListViewModel!!.update(alarm)
        } else {
            alarm.schedule(context)
            alarmsListViewModel!!.update(alarm)
        }
    }
}