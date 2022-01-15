package alarmslist

import data.Alarm

interface OnToggleAlarmListener {
    fun onToggle(alarm: Alarm?)
}



