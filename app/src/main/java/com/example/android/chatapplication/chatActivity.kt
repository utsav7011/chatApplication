package com.example.android.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView:RecyclerView
    private lateinit var mesageBox:EditText
    private lateinit var sentButton:ImageView

    private lateinit var messageAdapter:messageAdapter
    private lateinit var messageList: ArrayList<message>

    private lateinit var mDbRef:DatabaseReference

    // create a unique room for sender and receiver :
    var receiverRoom:String? = null
    var senderRoom:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mDbRef = FirebaseDatabase.getInstance().getReference()

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        // create a ui=nique room for sender and receiver :
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        // set the name in  the uid :
        supportActionBar?.title = name;

        messageRecyclerView = findViewById(R.id.chatRecyclerView)
        mesageBox = findViewById(R.id.messageBox)
        sentButton = findViewById(R.id.sendButton)

        messageList = ArrayList()
        messageAdapter = messageAdapter(this, messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        // logic to add data to recycler view:
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshop in snapshot.children){

                        val message = postSnapshop.getValue(message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        // handle the logic to add hte msg to dataabse :
        sentButton.setOnClickListener{
            // send the message to the database and then from the data abse mssage received to the database:
            var message = mesageBox.text.toString()
            val messageObject = message(message,senderUid)

            // create a node of chats  in the sneder room:
            mDbRef.child("chats").child(senderRoom!!).child("messages")
                .push().setValue(messageObject).addOnSuccessListener {
                    // update the recriver room :
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            mesageBox.setText("")

        }
    }
}