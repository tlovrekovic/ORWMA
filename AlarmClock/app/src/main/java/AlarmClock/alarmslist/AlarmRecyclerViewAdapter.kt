package AlarmClock.alarmslist

import AlarmClock.R
import alarmslist.AlarmViewHolder
import alarmslist.OnToggleAlarmListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import data.Alarm
import java.util.ArrayList

class AlarmRecyclerViewAdapter(listener: OnToggleAlarmListener?) :
    RecyclerView.Adapter<AlarmViewHolder?>() {
    private var alarms: MutableList<Alarm?>
    private val listener: OnToggleAlarmListener?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        if (alarm != null) {
            holder.bind(alarm)
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun setAlarms(alarms: MutableList<Alarm?>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: AlarmViewHolder) {
        super.onViewRecycled(holder)
        holder.alarmStarted?.setOnCheckedChangeListener(null)
    }

    init {
        alarms = ArrayList()
        this.listener = listener
    }
}