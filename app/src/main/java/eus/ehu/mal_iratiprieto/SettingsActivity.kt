package eus.ehu.mal_iratiprieto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import model.backend.Backend
import model.backend.Data
import model.backend.Locator


class SettingsActivity : AppCompatActivity() {

    private lateinit var data: Data
    private lateinit var timeID: EditText
    private lateinit var distanceID: EditText

    private lateinit var saveSettings_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setData()

        saveSettings_btn = findViewById(R.id.button_SaveSettings)
        saveSettings_btn.setOnClickListener {

            getData()

            var backend: Backend

            Locator.getBackend().setUserSettings()
                .addOnCompleteListener { task: Task<DataSnapshot?> ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Settings saved", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    finish()
                }

        }
    }
    private fun setData(){
        data = Data.getInstance()
        timeID = findViewById(R.id.textViewTime)
        distanceID = findViewById(R.id.textViewDistance)

        if(data.user.time!=0.0)
            timeID.hint = data.user.time.toString()
        if(data.user.distance!=0.0)
            distanceID.hint = data.user.distance.toString()

    }
    private fun getData(){
        data = Data.getInstance()
        timeID = findViewById(R.id.textViewTime)
        distanceID = findViewById(R.id.textViewDistance)
        var time = timeID.text.toString()
        var dist = distanceID.text.toString()
        if(time!= "") {
            if (time.contains("."))
                data.user.time = time.toDouble()
            else {
                time = time + ".0"
                data.user.time = time.toDouble()
            }
        }
        if(dist!= "") {
            if (dist.contains("."))
                data.user.distance = dist.toDouble()
            else {
                dist = dist + ".0"
                data.user.distance = dist.toDouble()
            }
        }



    }

    fun toSafeMapActivity(view: View) {
        val intent = Intent(this, SafeMapActivity::class.java)
        startActivity(intent)
    }
}