package eus.ehu.mal_iratiprieto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsService
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import model.backend.Backend
import model.backend.Data
import model.backend.Locator
import model.beans.UserProfile
import model.beans.loc
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class HomeActivity : AppCompatActivity(){
    private var data: Data = Data.getInstance()
    private var time: Long = 0
    private var dist: Double=0.0
    private lateinit var temporal: loc
    private lateinit var save_location_btn: Button
    private lateinit var textView_name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textView_name = findViewById(R.id.userName)
        textView_name.text = data.user.name


        time = (data.user.time).toLong()



       val executorService = Executors.newScheduledThreadPool(10)
        executorService.scheduleAtFixedRate({
            checkGuardado()}, 0, time, TimeUnit.MINUTES)




        save_location_btn = findViewById(R.id.button_SavePos)

        save_location_btn.setOnClickListener {

            val executorService2 = Executors.newScheduledThreadPool(10)
            executorService2.scheduleAtFixedRate({
                saveTempPos()}, 0, time, TimeUnit.MINUTES)

        }
    }
    fun toMapActivity(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
    fun toSettingsActivity(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun toFriendsActivity(view: View) {
        val intent = Intent(this, FriendsActivity::class.java)
        startActivity(intent)
    }

    private fun checkGuardado(){


        Locator.getBackend().temp
            .addOnCompleteListener { task: Task<UserProfile?> ->
                if (task.isSuccessful) {
                    //Toast.makeText(this, "Safe position saved", Toast.LENGTH_LONG).show()
                    //startActivity(Intent(this, SettingsActivity::class.java))
                }
              //  finish()
            }
        calcDistKm()

        if(data.user.distance <= dist)
            saveTempPos()



    }
    private fun saveTempPos(){


        Locator.getBackend().temp
            .addOnCompleteListener { task: Task<UserProfile?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Saving...", Toast.LENGTH_LONG).show()

                }
              //  finish()
            }
        Locator.getBackend().saveTemp()
            .addOnCompleteListener { task: Task<DataSnapshot?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Saving...", Toast.LENGTH_LONG).show()

                }

                //  finish()
            }
    }
    private fun calcDistKm(){
        var difLat = (data.user.tempLoc.latitude - data.user.safeLat)*Math.PI/180
        var difLong = (data.user.tempLoc.longitude - data.user.safeLong)*Math.PI/180

        var a = sin(difLat/2).pow(2) + cos(data.user.safeLat*Math.PI/180)*cos(data.user.tempLoc.latitude*Math.PI/180)*sin(difLong/2).pow(2)
        var c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a))

        dist = 6378*c
    }
}