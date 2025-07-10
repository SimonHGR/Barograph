package com.scratch.pressurektv1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.scratch.pressurektv1.ui.theme.PressureKTV1Theme
import java.time.ZonedDateTime

//class TimePressureTuple(time: ZonedDateTime, pressure: Double)

class MainActivity : ComponentActivity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var pressureSensor: Sensor? = null
    private var pressureReading = mutableDoubleStateOf(1013.0)
//    private var pressureReadings = mutableListOf(ArrayList<TimePressureTuple>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        pressureSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mSensorManager?.registerListener (this, pressureSensor, Sensor.REPORTING_MODE_ON_CHANGE)
        enableEdgeToEdge()
        setContent {
            PressureKTV1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        pressureReading,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nothing to do?
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // update text
        val v = event?.values
        if (v != null) {
            pressureReading.doubleValue = v[0].toDouble()
        }
    }
}

@Composable
fun Greeting(data: MutableDoubleState, modifier: Modifier = Modifier) {
    val value by remember { data }
    Text(
        text = "Value is " + data.doubleValue,
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    val data = mutableDoubleStateOf(1013.1)
//    PressureKTV1Theme {
//        Greeting(
//            "Android",
//            data
//        )
//    }
//}