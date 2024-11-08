package lab03.eim.systems.cs.pub.inreg_preg_colocviu1_acasa

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var count1 = 0
    private var count2 = 0
    private val REQUEST_CODE = 1
    private val THRESHOLD = 10

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val date = intent?.getStringExtra("date")
            val arithmeticMean = intent?.getDoubleExtra("arithmeticMean", 0.0)
            val geometricMean = intent?.getDoubleExtra("geometricMean", 0.0)
            Log.d("BroadcastReceiver", "Date: $date, Arithmetic Mean: $arithmeticMean, Geometric Mean: $geometricMean")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView1: TextView = findViewById(R.id.textView1)
        val textView2: TextView = findViewById(R.id.textView2)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val startSecondaryActivityButton: Button = findViewById(R.id.start_secondary_activity_button)

        button1.setOnClickListener {
            count1++
            textView1.text = count1.toString()
            checkAndStartService()
        }

        button2.setOnClickListener {
            count2++
            textView2.text = count2.toString()
            checkAndStartService()
        }

        startSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01SecondaryActivity::class.java)
            intent.putExtra("count", count1 + count2)
            startActivityForResult(intent, REQUEST_CODE)
        }

        if (savedInstanceState != null) {
            count1 = savedInstanceState.getInt("count1", 0)
            count2 = savedInstanceState.getInt("count2", 0)
            textView1.text = count1.toString()
            textView2.text = count2.toString()
        }

        val filter = IntentFilter().apply {
            addAction("ACTION_1")
            addAction("ACTION_2")
            addAction("ACTION_3")
        }
        ContextCompat.registerReceiver(this, broadcastReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
    }

    private fun checkAndStartService() {
        if (count1 + count2 > THRESHOLD) {
            val intent = Intent(this, PracticalTest01Service::class.java).apply {
                putExtra("count1", count1)
                putExtra("count2", count2)
            }
            startService(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count1", count1)
        outState.putInt("count2", count2)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count1 = savedInstanceState.getInt("count1", 0)
        count2 = savedInstanceState.getInt("count2", 0)
        findViewById<TextView>(R.id.textView1).text = count1.toString()
        findViewById<TextView>(R.id.textView2).text = count2.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val message = when (resultCode) {
                Activity.RESULT_OK -> "OK button pressed"
                Activity.RESULT_CANCELED -> "Cancel button pressed"
                else -> "Unknown result"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, PracticalTest01Service::class.java))
        unregisterReceiver(broadcastReceiver)
    }
}