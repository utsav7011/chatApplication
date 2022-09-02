package com.example.android.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.platforminfo.UserAgentPublisher

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView:RecyclerView
    private lateinit var  userList:ArrayList<user>
    private lateinit var adapter:userAdapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialization:
        userList = ArrayList();
        adapter = userAdapter(this, userList)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
//      till here;


        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                // snapshot used to get the data from the database :
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currnetUser = postSnapshot.getValue(user::class.java)

                    // checl if the uid of the users match for the
                    // current logged in user :

                    // do not sho the name of the logged in user :

                    if (mAuth.currentUser?.uid != currnetUser?.UID){
                        userList.add(currnetUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

//    inflate the menu to the main activity:
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout){
            // write the logic for logout:
            mAuth.signOut()

            val intnet = Intent(this,Login::class.java )
            finish()
            startActivity(intent)
            return true
        }

        return true

    }
    // class ends :
}