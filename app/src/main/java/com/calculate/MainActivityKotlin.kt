package com.calculate

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 位运算 kotlin版本
 */
class MainActivityKotlin: AppCompatActivity(){

    companion object{
        private const val WITH = 1
        private const val OR = 2
        private const val NON = 3
        private const val SHIFT_LEFT = 4
        private const val SHIFT_RIGHT = 5
        private const val NOT = 6
    }

    private lateinit var edA: EditText
    private lateinit var edB: EditText
    private lateinit var edC: EditText
    private lateinit var tvA: TextView
    private lateinit var tvB: TextView
    private lateinit var tvC: TextView
    private lateinit var flB: View
    private lateinit var flB1: View
    private lateinit var vA: View
    private lateinit var vB: View
    private lateinit var vC: View

    private lateinit var toast: Toast
    private var type = 0
    private var useLong = false
    private var normal = 0f
    private var small = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        normal = resources.displayMetrics.density * 14
        small = resources.displayMetrics.density * 9
        initView()
    }

    private fun initView() {
        edA = findViewById(R.id.edA)
        edB = findViewById(R.id.edB)
        edC = findViewById(R.id.edC)
        tvA = findViewById(R.id.tvA)
        tvB = findViewById(R.id.tvB)
        tvC = findViewById(R.id.tvC)
        flB = findViewById(R.id.flB)
        flB1 = findViewById(R.id.flB1)
        vA = findViewById(R.id.vA)
        vB = findViewById(R.id.vB)
        vC = findViewById(R.id.vC)

        findViewById<RadioGroup>(R.id.rgGroup).setOnCheckedChangeListener{_,item->
            when (item) {
                R.id.with -> type = WITH
                R.id.or -> type = OR
                R.id.non -> type = NON
                R.id.shift_left -> type = SHIFT_LEFT
                R.id.shift_right -> type = SHIFT_RIGHT
                R.id.not -> type = NOT
            }
            if (item == R.id.non) {
                flB.visibility = View.GONE
                flB1.visibility = View.GONE
            } else {
                flB.visibility = View.VISIBLE
                flB1.visibility = View.VISIBLE
            }
        }

        findViewById<CheckBox>(R.id.cbType).setOnCheckedChangeListener{_,use->
            useLong = use
            if (use) {
                tvA.paint.textSize = small
                tvB.paint.textSize = small
                tvC.paint.textSize = small
                vA.visibility = View.GONE
                vB.visibility = View.GONE
                vC.visibility = View.GONE
            } else {
                tvA.paint.textSize = normal
                tvB.paint.textSize = normal
                tvC.paint.textSize = normal
                vA.visibility = View.VISIBLE
                vB.visibility = View.VISIBLE
                vC.visibility = View.VISIBLE
            }
            tvA.text = ""
            tvB.text = ""
            tvC.text = ""
        }
    }

    private fun getLong(text: String): Long {
        try {
            return text.toLong()
        } catch (e: Exception) {
        }
        return 0
    }

    private fun getInt(text: String): Int {
        try {
            return text.toInt()
        } catch (e: Exception) {
        }
        return 0
    }

    private fun binaryString(number: Long): String {
        val builder = StringBuilder()
        for (i in 63 downTo 0) {
            builder.append(number shr i and 1)
        }
        return builder.toString()
    }

    private fun binaryString(number: Int): String {
        val builder = StringBuilder()
        for (i in 31 downTo 0) {
            builder.append(number shr i and 1)
        }
        return builder.toString()
    }

    private fun onToast(id: Int) {
        toast.setText(id)
        toast.show()
    }

    fun onCalculate(view: View) {
        if (type == 0) {
            onToast(R.string.toast_1)
            return
        }
        val A = edA.text.toString()
        if (A.isEmpty()) {
            onToast(R.string.input_a)
            return
        }
        val B: String = edB.getText().toString()
        if (type != NON && A.isEmpty()) {
            onToast(R.string.input_b)
            return
        }
        if (useLong) {
            val valueA = getLong(A)
            val valueB = getLong(B)
            tvA.text = binaryString(valueA)
            tvB.text = binaryString(valueB)
            var valueC: Long = 0
            when (type) {
                WITH -> valueC = valueA and valueB
                OR -> valueC = valueA or valueB
                NON -> valueC = valueA.inv()
                SHIFT_LEFT -> valueC = valueA shl valueB.toInt()
                SHIFT_RIGHT -> valueC = valueA shr valueB.toInt()
                NOT -> valueC = valueA xor valueB
            }
            edC.setText(valueC.toString())
            tvC.text = binaryString(valueC)
        } else {
            val valueA = getInt(A)
            val valueB = getInt(B)
            tvA.text = binaryString(valueA)
            tvB.text = binaryString(valueB)
            var valueC = 0
            when (type) {
                WITH -> valueC = valueA and valueB
                OR -> valueC = valueA or valueB
                NON -> valueC = valueA.inv()
                SHIFT_LEFT -> valueC = valueA shl valueB
                SHIFT_RIGHT -> valueC = valueA shr valueB
                NOT -> valueC = valueA xor valueB
            }
            edC.setText(valueC.toString())
            tvC.text = binaryString(valueC)
        }
    }
}