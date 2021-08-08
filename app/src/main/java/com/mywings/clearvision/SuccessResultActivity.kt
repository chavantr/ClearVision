package com.mywings.clearvision

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_success_result.*
import java.text.SimpleDateFormat
import java.util.*

class SuccessResultActivity : AppCompatActivity() {

    private val notificationUtils = NotificationUtils()
    private val hours = mutableListOf(8, 13, 20)
    private lateinit var textToSpeech: TextToSpeech
    private var textToSpeechListener =
        TextToSpeech.OnInitListener { textToSpeech.language = Locale.US }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_result)
        textToSpeech = TextToSpeech(this, textToSpeechListener)
        val items = intent.extras?.getString("extra")?.split("#")
        if (items?.isNotEmpty() == true && items.size >= 9) {
            txtNameValue.text = items[0]
            txtDNameValue.text = items[1]
            txtTNameValue.text = items[2]
            txtDesNameValue.text = items[3]
            txtQuantityValue.text = items[4]
            txtExpiryValue.text = items[5]
            val times = items[6].split("@")
            if (times.size == 3) {
                txtMorningValue.text = times[0]
                txtNoonValue.text = times[1]
                txtNightValue.text = times[2]
            }
            txtNoValue.text = items[7]
            txtTypeTitle.text = items[8]
            txtTypeValue.text = items[9]

            val accessibilityManager =
                getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            if (!accessibilityManager.isEnabled) {
                generate(items)
            }
            if (intent.getBooleanExtra("flag", false)) {
                val id = (Date().time / 1000L % Int.MAX_VALUE).toInt()
                hours.forEach {
                    getNotification(
                        hour = it,
                        id = id,
                        medicineDetails = "\n Medicine name ${items[2]}\n Description ${items[3]}\n Quantity ${items[4]}"
                    )
                }
            }
        }
    }

    private fun delayIn() {
        textToSpeech.playSilence(750L, TextToSpeech.QUEUE_ADD, null)
    }

    private fun generate(items: List<String>) {
        val myHash = HashMap<String, String>()
        myHash[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "done"

        textToSpeech.speak(
            getString(R.string.medicine_name).plus(items[2]),
            TextToSpeech.QUEUE_FLUSH,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            getString(R.string.description).plus(items[3]),
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            getString(R.string.quantity).plus(items[4]),
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            getString(R.string.expiry_date).plus(items[5]),
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()
    }

    private fun getNotification(hour: Int, id: Int, medicineDetails: String) {
        notificationUtils.scheduleNotification(
            notificationUtils.getNotification(
                "Your medicine time ".plus(medicineDetails),
                this
            ),
            getTime(hour), this, id
        )
    }

    private fun getTime(hour: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val dateInString = calendar.get(Calendar.DAY_OF_MONTH).toString().plus("/")
            .plus(calendar.get(Calendar.MONTH).plus(1).toString()).plus("/")
            .plus(calendar.get(Calendar.YEAR).toString()).plus(" $hour").plus(":0:0")
        val date: Date = sdf.parse(dateInString)
        return date.time
    }
}