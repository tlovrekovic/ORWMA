package alarmslist

import AlarmClock.R
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import data.Alarm

class AlarmViewHolder(itemView: View, listener: OnToggleAlarmListener?) :
    RecyclerView.ViewHolder(itemView) {
    private val alarmTime: TextView?
    private val alarmRecurring: ImageView?
    private val alarmRecurringDays: TextView?
    private val alarmTitle: TextView?
    var alarmStarted: SwitchCompat?
    private val listener: OnToggleAlarmListener?
    fun bind(alarm: Alarm) {
        val alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute())
        alarmTime!!.text = alarmText
        alarmStarted!!.isChecked = alarm.isStarted()
        if (alarm.isRecurring()) {
            alarmRecurring!!.setImageResource(R.drawable.ic_repeat_black_24dp)
            alarmRecurringDays!!.text = alarm.getRecurringDaysText()
        } else {
            alarmRecurring!!.setImageResource(R.drawable.ic_looks_one_black_24dp)
            alarmRecurringDays!!.text = "Once Off"
        }
        if (alarm.getTitle()!!.isNotEmpty()) {
            alarmTitle!!.text = String.format(
                "%s | %d | %d",
                alarm.getTitle(),
                alarm.getAlarmId(),
                alarm.getCreated()
            )
        } else {
            alarmTitle!!.text = String.format(
                "%s | %d | %d",
                "Alarm",
                alarm.getAlarmId(),
                alarm.getCreated()
            )
        }
        alarmStarted!!.setOnCheckedChangeListener { buttonView, isChecked ->
            listener!!.onToggle(
                alarm
            )
        }
    }

    init {
        alarmTime = itemView.findViewById(R.id.item_alarm_time)
        alarmStarted = itemView.findViewById(R.id.item_alarm_started)
        alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring)
        alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays)
        alarmTitle = itemView.findViewById(R.id.item_alarm_title)
        this.listener = listener
    }
}