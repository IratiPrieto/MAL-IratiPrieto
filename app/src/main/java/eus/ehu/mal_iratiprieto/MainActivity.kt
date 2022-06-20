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
    //private lateinit var mProgressBar: ProgressDialog

    //Creamos nuestra variable de autenticación firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialise()
    }

    /*Creamos un método para inicializar nuestros elementos del diseño y la autenticación de firebase*/
    private fun initialise() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
      //  mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
    }

//Ahora vamos a Iniciar sesión con firebase es muy sencillo

    private fun loginUser() {
        //Obtenemos usuario y contraseña
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        //Verificamos que los campos no este vacios
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            //Mostramos el progressdialog
     //       mProgressBar.setMessage("Registering User...")
      //      mProgressBar.show()

            //Iniciamos sesión con el método signIn y enviamos usuario y contraseña
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    //Verificamos que la tarea se ejecutó correctamente
                        task ->
                    if (task.isSuccessful) {
                        // Si se inició correctamente la sesión vamos a la vista Home de la aplicación

                        goHome() // Creamos nuestro método en la parte de abajo
                    } else {
                        // sino le avisamos el usuairo que orcurrio un problema
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

/* Tenemos que crear nuestros métodos con el mismo nombre que le agregamos a nuestros botones en el atributo onclick y forzosamente tenemos que recibir un parámetro view para que sea reconocido como click de nuestro button ya que en view podemos modificar los atributos*/

    /*Primero creamos nuestro evento login dentro de este llamamos nuestro método loginUser al dar click en el botón se iniciara sesión */
    fun login(view: View) {
        loginUser()
    }

/*Si se olvido de la contraseña lo enviaremos en la siguiente actividad nos marcara error porque necesitamos crear la actividad*/

    fun forgotPassword(view: View) {
        startActivity(Intent(this,
            ForgotPasswordActivity::class.java))
    }

/*Si quiere registrarse lo enviaremos en la siguiente actividad nos marcara error porque necesitamos crear la actividad*/

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}