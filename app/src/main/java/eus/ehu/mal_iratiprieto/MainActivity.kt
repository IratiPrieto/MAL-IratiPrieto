package eus.ehu.mal_iratiprieto

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import model.backend.Backend
import model.backend.Data
import model.backend.Locator
import model.beans.UserProfile
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    //global variables
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var data: Data

    //Firebaserako autentikazio aldagaia
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialise()
    }


    private fun initialise() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
      //  mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
    }

//Saioa hasi

    private fun loginUser() {
        //Erabiltzaile eta pasahitza lortu
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        //Ezin dira hutsik egon
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {



            //Saioa hasi
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                        task ->
                    if (task.isSuccessful) {
                        // Saioa ondo hasi da

                        goHome()
                    } else {
                        // Ez da ondo hasi
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }


    private fun goHome() {
        val user = FirebaseAuth.getInstance().currentUser!!
        data = Data.getInstance()
        data.user.id = user.uid
        data.user.name = user.displayName

        Log.e(TAG, ""+data.user.id)
        Log.e(TAG, ""+user.displayName)
//Nos vamos a Home

        var backend: Backend

        Locator.getBackend().userData
            .addOnCompleteListener { task: Task<UserProfile?> ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                finish()
            }



    }


    fun login(view: View) {
        loginUser()
    }


    fun forgotPassword(view: View) {
        startActivity(Intent(this,
            ForgotPasswordActivity::class.java))
    }


    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}