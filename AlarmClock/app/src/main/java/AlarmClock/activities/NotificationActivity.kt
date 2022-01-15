package AlarmClock.activities

import AlarmClock.R
import Service.AlarmService
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import data.Alarm
import kotlin.random.Random
import java.util.*


//dismiss gumbom zaustvaljamo AlarmService i pozivamo stopService(intent) na kotekst aplikacije
// finish() zaustvalja servis
class NotificationActivity : AppCompatActivity() {
    var dismiss = findViewById<Button>(R.id.activity_ring_dismiss)
    var snooze = findViewById<Button>(R.id.activity_ring_snooze)
    var clock = findViewById<ImageView>(R.id.activity_ring_clock)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifitcation)
        dismiss?.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        //snooze gumb radi isto samo prvo zakaže novi alarm 10 minuta u buducnost
        snooze?.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 10)
            val alarm = Alarm(
                Random.nextInt(Int.MAX_VALUE),
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                "Snooze",
                System.currentTimeMillis(),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            )
            alarm.schedule(applicationContext)
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        animateClock()
    }

    private fun animateClock() {
        val rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f)
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.duration = 800
        rotateAnimation.start()
    }
}