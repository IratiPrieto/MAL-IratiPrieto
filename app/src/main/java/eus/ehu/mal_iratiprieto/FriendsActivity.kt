package eus.ehu.mal_iratiprieto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import model.backend.Data
import model.backend.Locator
import model.beans.UserProfile

class FriendsActivity : AppCompatActivity() {

    private lateinit var data: Data
    private lateinit var delete_btn1: FloatingActionButton
    private lateinit var view_btn1: FloatingActionButton
    private lateinit var delete_btn2: FloatingActionButton
    private lateinit var view_btn2: FloatingActionButton
    private lateinit var delete_btn3: FloatingActionButton
    private lateinit var view_btn3: FloatingActionButton
    private lateinit var delete_btn4: FloatingActionButton
    private lateinit var view_btn4: FloatingActionButton
    private lateinit var delete_btn5: FloatingActionButton
    private lateinit var view_btn5: FloatingActionButton
    private lateinit var add_btn: FloatingActionButton

    private lateinit var textV: TextView
    private lateinit var newMail: EditText
    private var m_Text = ""
    private var friendID = ""
    private lateinit var ds: DataSnapshot
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        data = Data.getInstance()

        delete_btn1 = findViewById(R.id.btn_removeFriend1)
        view_btn1 = findViewById(R.id.btn_viewFriend1)
        delete_btn2 = findViewById(R.id.btn_removeFriend2)
        view_btn2 = findViewById(R.id.btn_viewFriend2)
        delete_btn3 = findViewById(R.id.btn_removeFriend3)
        view_btn3 = findViewById(R.id.btn_viewFriend3)
        delete_btn4 = findViewById(R.id.btn_removeFriend4)
        view_btn4 = findViewById(R.id.btn_viewFriend4)
        delete_btn5 = findViewById(R.id.btn_removeFriend5)
        view_btn5 = findViewById(R.id.btn_viewFriend5)


        showFriends()


        add_btn = findViewById(R.id.btn_new_friend)
        newMail = findViewById(R.id.editText_new_email)

        add_btn.setOnClickListener {
            addFriend()
        }

        delete_btn1.setOnClickListener {
            deleteFriend(1)
        }
        view_btn1.setOnClickListener {
            viewFriend(1)
        }
        delete_btn2.setOnClickListener {
            deleteFriend(2)
        }
        view_btn2.setOnClickListener {
            viewFriend(2)
        }
        delete_btn3.setOnClickListener {
            deleteFriend(3)
        }
        view_btn3.setOnClickListener {
            viewFriend(3)
        }
        delete_btn4.setOnClickListener {
            deleteFriend(4)
        }
        view_btn4.setOnClickListener {
            viewFriend(4)
        }
        delete_btn5.setOnClickListener {
            deleteFriend(5)
        }
        view_btn5.setOnClickListener {
            viewFriend(5)
        }



    }

    private fun deleteFriend(i: Int){
        Locator.getBackend().deleteFriend(i)
            .addOnCompleteListener { task: Task<DataSnapshot> ->
                if (task.isSuccessful) {
                    Log.e(TAG, "Friend deleted")
                    data.user.delFriend(i-1)
                    finish()
                    startActivity(getIntent())
                }
                //  finish()
            }
    }
    private fun viewFriend(i: Int){
        Locator.getBackend().getFriendLoc(i)
            .addOnCompleteListener { task: Task<UserProfile> ->
                if (task.isSuccessful) {
                    val intent = Intent(this, FriendMapsActivity::class.java)
                    startActivity(intent)
                }
                //  finish()
            }
    }
    companion object {
        private const val TAG = "FriendsActivity" // for debugging
    }
    fun showFriends(){
        var j = 1
        for(i in data.user.friends) {
            when (data.user.friends.indexOf(i)) {
                0 -> {
                    data.user.check = false
                    textV = findViewById(R.id.txt_friend1)
                    delete_btn1.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = data.user.friendsEmails[data.user.friends.indexOf(i)]
                    Locator.getBackend().checkFriend(i)
                        .addOnCompleteListener { task: Task<DataSnapshot> ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Check: " + data.user.check + " ID: " + data.user.newFriend + " check: "+ i)
                                var ch = data.user.check
                                if (ch==true){
                                    view_btn1.visibility = android.view.View.VISIBLE
                                    if(!data.user.friendsEmails.contains(textV.text.toString()))
                                        data.user.setFriendEmail(data.user.friendsEmails.size, textV.text.toString())
                                }

                                else
                                    view_btn1.visibility = android.view.View.INVISIBLE
                            }
                            else
                                view_btn1.visibility = android.view.View.INVISIBLE
                            //  finish()
                        }

                }
                1 -> {
                    data.user.check = false
                    textV = findViewById(R.id.txt_friend2)
                    delete_btn2.visibility = android.view.View.VISIBLE
                    view_btn2.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = data.user.friendsEmails[data.user.friends.indexOf(i)]
                    Locator.getBackend().checkFriend(i)
                        .addOnCompleteListener { task: Task<DataSnapshot> ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Check: " + data.user.check + " ID: " + data.user.newFriend + " check: "+ i)
                                var ch = data.user.check
                                if (ch==true){
                                    view_btn2.visibility = android.view.View.VISIBLE

                                }

                                else
                                    view_btn2.visibility = android.view.View.INVISIBLE

                            }else
                                view_btn2.visibility = android.view.View.INVISIBLE
                            //  finish()
                        }

                }
                2 -> {
                    data.user.check = false
                    textV = findViewById(R.id.txt_friend3)
                    delete_btn3.visibility = android.view.View.VISIBLE
                    view_btn3.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = data.user.friendsEmails[data.user.friends.indexOf(i)]
                    Locator.getBackend().checkingFriend(i)
                    Locator.getBackend().checkFriend(i)
                        .addOnCompleteListener { task: Task<DataSnapshot> ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Check: " + data.user.check)
                                var ch = data.user.check
                                if (ch==true)
                                    view_btn3.visibility = android.view.View.VISIBLE
                                else
                                    view_btn3.visibility = android.view.View.INVISIBLE
                            }else
                                view_btn3.visibility = android.view.View.INVISIBLE
                            //  finish()
                        }

                }
                3 -> {
                    data.user.check = false
                    textV = findViewById(R.id.txt_friend4)
                    delete_btn4.visibility = android.view.View.VISIBLE
                    view_btn4.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    textV.text = data.user.friendsEmails[data.user.friends.indexOf(i)]
                    Locator.getBackend().checkingFriend(i)
                    Locator.getBackend().checkFriend(i)
                        .addOnCompleteListener { task: Task<DataSnapshot> ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Check: " + data.user.check)
                                var ch = data.user.check
                                if (ch==true)
                                    view_btn4.visibility = android.view.View.VISIBLE
                                else
                                    view_btn4.visibility = android.view.View.INVISIBLE
                            }else
                                view_btn4.visibility = android.view.View.INVISIBLE
                            //  finish()
                        }
                }
                4 -> {
                    data.user.check = false
                    textV = findViewById(R.id.txt_friend5)
                    delete_btn5.visibility = android.view.View.VISIBLE
                    view_btn5.visibility = android.view.View.VISIBLE
                    textV.visibility = android.view.View.VISIBLE
                    add_btn.visibility = android.view.View.INVISIBLE
                    newMail.visibility = android.view.View.INVISIBLE
                    textV.text = data.user.friendsEmails[data.user.friends.indexOf(i)]
                    Locator.getBackend().checkFriend(i)
                        .addOnCompleteListener { task: Task<DataSnapshot> ->
                            if (task.isSuccessful) {
                                Log.e(TAG, "Check: " + data.user.check)
                                var ch = data.user.check
                                if (ch==true)
                                    view_btn5.visibility = android.view.View.VISIBLE
                                else
                                    view_btn5.visibility = android.view.View.INVISIBLE
                            }else
                                view_btn5.visibility = android.view.View.INVISIBLE
                            //  finish()
                        }

                }
            }

        }
    }
    private fun addFriend() {
        if(newMail.text.toString()!=""){
            m_Text = newMail.text.toString()
            data.user.m_text = m_Text
            Locator.getBackend().friendID
                .addOnCompleteListener { task: Task<DataSnapshot> ->
                    if (task.isSuccessful) {
                        Log.e(TAG, " ID: " + data.user.newFriend)

                        //Toast.makeText(this, "Safe position saved", Toast.LENGTH_LONG).show()
                        //startActivity(Intent(this, SettingsActivity::class.java))
                    }

                    //  finish()
                }
                .continueWith(Continuation<DataSnapshot?, DataSnapshot?> {
                    (if(data.user.friendExists){
                        Locator.getBackend().addFriend()
                            .addOnCompleteListener { task: Task<DataSnapshot> ->
                                if (task.isSuccessful) {
                                    finish()
                                    startActivity(getIntent())
                                    //Toast.makeText(this, "Safe position saved", Toast.LENGTH_LONG).show()
                                    //startActivity(Intent(this, SettingsActivity::class.java))
                                }

                                //  finish()
                            }
                    }else{
                        Toast.makeText(this, "Friend account doesn't exist", Toast.LENGTH_LONG).show()
                    }) as DataSnapshot?
                })



            //startActivity(Intent(this, FriendsActivity::class.java))
        }
    }




    /*fun getFriendID(){
        val rootRef = FirebaseDatabase.getInstance().reference
        val usersdRef = rootRef.child("Users")
        usersdRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val name = ds.child("email").getValue(String::class.java)
                    Log.d("TAG", name!!)
                    if(name == m_Text){
                        val key = ds.key.toString()
                        data.user.newFriend = key
                    }


           //         addFriend()
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }*/
    /*private fun add(){
        //       user = new UserProfile();
        val dbRef = FirebaseDatabase.getInstance().reference

        val rootRef =
            FirebaseDatabase.getInstance().getReference("sessions/" + data.user.id)
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("friends")) {
                    val map = HashMap<String, Any>()
                    map.set(data.user.newFriend, data.user.newFriend)
                    dbRef.child("sessions").child(data.user.id).child("friends")
                        .updateChildren(map)
                    data.user.addFriend(data.user.newFriend)
                } else {
                    val map = HashMap<String, Any>()
                    map.set("friends", "friends")
                    dbRef.child("sessions").child(data.user.id).updateChildren(map)
                    val map2 = HashMap<String, Any>()
                    map2.set(data.user.newFriend, data.user.newFriend)
                    dbRef.child("sessions").child(data.user.id).child("friends")
                        .updateChildren(map2)
                    data.user.addFriend(data.user.newFriend)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //some code
            }
        })
    }*/
}


