package Service

import AlarmClock.R
import AlarmClock.activities.NotificationActivity
import BroadcastReciever.AlarmBroadcastReceiver
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import application.App
//ova klasa proširuje Service klasu

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    //onCreate se poziva kad je Service stvoren, postavljam media player da učita MP3 file
    //loopam zvuk... vibracija
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer?.setLooping(true)
        vibrator = getSystemService(VIBRATOR_MANAGER_SERVICE) as Vibrator
    }
//onStartCommand poziva se nakon onCreate() metode
    //stvaramo intent i pendingintent da omogucimo obajvesti da dopusti navigavije do aktivnosti da dismissa ili snooza alarm kad je obavjest odabrana
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        //dohvaca se naslov za uporabu u obavjesti
        val alarmTitle =
            String.format("%s Alarm",
                intent?.getStringExtra(AlarmBroadcastReceiver.Companion.TITLE)
            )
        //stvara i salje obavjest koja sardzava naslov alarma, ikonu i dismiss, snooze gumbe
        val notification: Notification = NotificationCompat.Builder(this,
            App.Companion.CHANNEL_ID.toString()
        )
            .setContentTitle(alarmTitle)
            .setContentText("Ring Ring .. Ring Ring")
            .setSmallIcon(R.drawable.ic_alarm_black_24dp)
            .setContentIntent(pendingIntent)
            .build()
        //pokreni media player i vibracije
        mediaPlayer?.start()
        //val pattern = longArrayOf(0, 100, 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(2,3))
        }
        startForeground(1, notification)
        return START_STICKY
    }

    //metoda koja se poziva kad je service nepotreban(clean up zadaće)
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        vibrator?.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}