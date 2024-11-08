package lab03.eim.systems.cs.pub.inreg_preg_colocviu1_acasa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_secondary)

        val textView: TextView = findViewById(R.id.textView)
        val okButton: Button = findViewById(R.id.ok_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        val count = intent.getIntExtra("count", 0)
        textView.text = count.toString()

        okButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}