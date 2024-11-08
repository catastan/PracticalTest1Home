package lab03.eim.systems.cs.pub.inreg_preg_colocviu1_acasa

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class PracticalTest01Service : Service() {

    private val handler = Handler()
    private val random = Random()
    private val actions = arrayOf("ACTION_1", "ACTION_2", "ACTION_3")
    private var count1 = 0
    private var count2 = 0

    private val broadcastRunnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val date = Date(currentTime)
            val arithmeticMean = (count1 + count2) / 2.0
            val geometricMean = sqrt(count1.toDouble() * count2.toDouble())

            val intent = Intent(actions[random.nextInt(actions.size)]).apply {
                putExtra("date", date.toString())
                putExtra("arithmeticMean", arithmeticMean)
                putExtra("geometricMean", geometricMean)
            }
            sendBroadcast(intent)
            handler.postDelayed(this, 10000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        count1 = intent?.getIntExtra("count1", 0) ?: 0
        count2 = intent?.getIntExtra("count2", 0) ?: 0
        handler.post(broadcastRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(broadcastRunnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}