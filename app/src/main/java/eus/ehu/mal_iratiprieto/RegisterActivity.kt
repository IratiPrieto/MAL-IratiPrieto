package eus.ehu.mal_iratiprieto

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import model.backend.Backend
import model.backend.Data
import model.backend.Locator
import model.beans.UserProfile
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {
    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    //global variables
    private var firstName by Delegates.notNull<String>()
    private var lastName by Delegates.notNull<String>()
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private var uid by Delegates.notNull<String>()
    private lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialise()
        data = Data.getInstance()
    }


    private fun initialise() {
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        databaseReference = database.reference.child("Users")
    }

    private fun createNewAccount() {

        //Get data
        firstName = txtName.text.toString()
        lastName = txtLastName.text.toString()
        email = txtEmail.text.toString()
        password = txtPassword.text.toString()

//Everything filled up
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            //Create user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    val user:FirebaseUser = auth.currentUser!!


                    verifyEmail(user);

                    val profileUpdates = UserProfileChangeRequest.Builder().apply {
                        displayName = firstName
                    }.build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                        }
                    }


                    val currentUserDb = databaseReference.child(user.uid)

                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    currentUserDb.child("email").setValue(email)
                    uid = user.uid
                    data.user.id = uid

                    val dbRef = FirebaseDatabase.getInstance().reference
                    dbRef.child("sessions").child(uid).setValue(uid)

                    data.user.distance = 1.0
                    data.user.safeLat = 43.257
                    data.user.safeLong = -2.92344
                    data.user.time = 1.0
                    dbRef.child("sessions").child(data.user.id).child("distance").setValue(1)
                    dbRef.child("sessions").child(data.user.id).child("safeLat").setValue(43.257)
                    dbRef.child("sessions").child(data.user.id).child("safeLong").setValue(-2.92344)
                    dbRef.child("sessions").child(data.user.id).child("time").setValue(1)

                    data.user.name = firstName


                    updateUserInfoAndGoHome()

                }.addOnFailureListener{
//Register failed
                    Toast.makeText(this, "Authentication error",
                        Toast.LENGTH_SHORT).show()
                }

        } else {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    //Kontua sortu
    fun register(view: View){
        createNewAccount()
    }

    private fun updateUserInfoAndGoHome() {

        //GO HOME
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

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener(this) {
            //Ondo egin da
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,
                        "Email " + user.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "Error al verificar el correo ",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}