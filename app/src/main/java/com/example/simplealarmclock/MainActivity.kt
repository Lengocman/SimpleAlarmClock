package com.example.simplealarmclock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var selectedTimeText: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 100, 50, 100)
        }

        timePicker = TimePicker(this).apply {
            setIs24HourView(true)
        }

        selectedTimeText = TextView(this).apply {
            textSize = 18f
            text = "Select alarm time"
        }

        setAlarmButton = Button(this).apply {
            text = "Set Alarm"
            setOnClickListener { setAlarm() }
        }

        layout.addView(selectedTimeText)
        layout.addView(timePicker)
        layout.addView(setAlarmButton)

        setContentView(layout)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setAlarm() {
        val hour = timePicker.hour
        val minute = timePicker.minute

        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minute)
            putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up for class!")
        }

        val resolvedActivity = intent.resolveActivity(packageManager)
        Log.d("AlarmDebug", "Resolved activity: $resolvedActivity")

        if (resolvedActivity != null) {
            startActivity(intent)
            Toast.makeText(this, "Alarm request sent successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No compatible alarm app found on this device", Toast.LENGTH_LONG).show()
        }
    }
}
