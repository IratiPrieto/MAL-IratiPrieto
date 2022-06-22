package eus.ehu.mal_iratiprieto

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import model.backend.Data

class FriendsActivity : AppCompatActivity() {

    private lateinit var data: Data
    private lateinit var delete_btn: FloatingActionButton
    private lateinit var add_btn: Button
    private lateinit var view_btn: FloatingActionButton
    private lateinit var textV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        data = Data.getInstance()
        var j = 1
        for(i in data.user.friends) {
            when (j) {
                1 -> {
                    delete_btn  = findViewById(R.id.btn_removeFriend1)
                    view_btn  = findViewById(R.id.btn_viewFriend1)
                    textV = findViewById(R.id.txt_friend1)
                    delete_btn.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = i
                    if(checkFriend(i))
                        view_btn.visibility = android.view.View.VISIBLE
                }
                2 -> {
                    delete_btn  = findViewById(R.id.btn_removeFriend2)
                    view_btn  = findViewById(R.id.btn_viewFriend2)
                    textV = findViewById(R.id.txt_friend2)
                    delete_btn.visibility = android.view.View.VISIBLE
                    view_btn.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = i
                    if(checkFriend(i))
                        view_btn.visibility = android.view.View.VISIBLE
                }
                3 -> {
                    delete_btn  = findViewById(R.id.btn_removeFriend3)
                    view_btn  = findViewById(R.id.btn_viewFriend3)
                    textV = findViewById(R.id.txt_friend3)
                    delete_btn.visibility = android.view.View.VISIBLE
                    view_btn.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = i
                    if(checkFriend(i))
                        view_btn.visibility = android.view.View.VISIBLE
                }
                4 -> {
                    delete_btn  = findViewById(R.id.btn_removeFriend4)
                    view_btn  = findViewById(R.id.btn_viewFriend4)
                    textV = findViewById(R.id.txt_friend4)
                    delete_btn.visibility = android.view.View.VISIBLE
                    view_btn.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = i
                    if(checkFriend(i))
                        view_btn.visibility = android.view.View.VISIBLE
                }
                5 -> {
                    delete_btn  = findViewById(R.id.btn_removeFriend5)
                    view_btn  = findViewById(R.id.btn_viewFriend5)
                    textV = findViewById(R.id.txt_friend5)
                    add_btn  = findViewById(R.id.btn_newFriend)
                    delete_btn.visibility = android.view.View.VISIBLE
                    view_btn.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    add_btn.visibility = android.view.View.INVISIBLE
                    textV.text = i
                    if(checkFriend(i))
                        view_btn.visibility = android.view.View.VISIBLE
                }
            }


            j++
        }

    }
    fun checkFriend(idFriend: String): Boolean{
        var friend = false


        //viewModel.getAnswersFromUser(this);
        val dbRef = FirebaseDatabase.getInstance().reference

        val sessionRef = FirebaseDatabase.getInstance().getReference("sessions/$idFriend")

        sessionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Exist! Do whatever.
                    dbRef.child("sessions").child(idFriend).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                if(snapshot.child("friends").exists()){
                                    if(snapshot.child("friends").hasChild(data.user.id))
                                        friend = true
                                }
                            }
                        }
                } else {
                    // Don't exist! Do something.

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })



        return friend
    }
}