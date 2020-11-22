package com.example.lab7_2_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var edHeight: EditText? = null
    private var edWeight: EditText? = null
    private var btnBoy: RadioButton? = null
    private var tvWeight: TextView? = null
    private var tvBmi: TextView? = null
    private var tvProgress: TextView? = null
    private var llProgress: LinearLayout? = null
    private var progressBar2: ProgressBar? = null
    private var btnCal: Button? = null
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edHeight = findViewById(R.id.ed_height)
        edWeight = findViewById(R.id.ed_weight)
        btnBoy = findViewById(R.id.btn_boy)
        tvWeight = findViewById(R.id.textView4)
        tvBmi = findViewById(R.id.textView5)
        llProgress = findViewById(R.id.ll_progress)
        progressBar2 = findViewById(R.id.progressBar3)
        tvProgress = findViewById(R.id.textView6)
        btnCal = findViewById(R.id.button3)
        btnCal?.setOnClickListener {
            if(edHeight?.length() == 0 )
                Toast.makeText(this@MainActivity,
                    "請輸入身高",Toast.LENGTH_SHORT).show()
            else if(edWeight?.length() == 0)
                Toast.makeText(this@MainActivity,
                    "請輸入體重",Toast.LENGTH_SHORT).show()
            else{
                job = GlobalScope.launch(Dispatchers.Main){
                    runCoroutine()
                }
            }
        }
    }
    private suspend fun runCoroutine() = withContext(Dispatchers.Main){
        tvWeight?.text = "標準體重\n無"
        tvBmi?.text = "體脂肪\n無"
        progressBar2?.progress = 0
        tvProgress?.text = "0%"
        llProgress?.visibility = View.VISIBLE
        var pro = 0
        while (pro <= 100) {
            try {
                delay(50)
                progressBar2?.progress= pro
                tvProgress?.text = "$pro%"
                pro++
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        llProgress?.visibility = View.GONE

        val h = Integer.valueOf(edHeight?.text.toString())
        val w = Integer.valueOf(edWeight?.text.toString())
        val standWeight: Double
        val bodyfat: Double
        if (btnBoy?.isChecked == true) {
            standWeight = (h - 80) * 0.7
            bodyfat = (w - 0.88 * standWeight) / w * 100
        } else {
            standWeight = (h - 70) * 0.7
            bodyfat = (w - 0.82 * standWeight) / w * 100
        }
        tvWeight?.text = String.format("標準體重\n%.2f", standWeight)
        tvBmi?.text = String.format("體脂肪\n%.2f", bodyfat)
    }
}
