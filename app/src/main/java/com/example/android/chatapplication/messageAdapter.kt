package com.example.android.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class messageAdapter (val context:Context,val messageList:ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1;
    val ITEM_SEND = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            // inflate the receive :
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return receiveViewHolder(view)

        }else{
            //inflate the sent :
            val view:View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return sentViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // read the list of messages :
        val currentMessage = messageList[position]

        if (holder.javaClass == sentViewHolder::class.java){
            // do the stuff for sent view holder :

            // type cast the holder to sent ViewHolder :
            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else{
            // do stuff for receive view holder :
            val viewHolder = holder as receiveViewHolder
            holder.receiveMessage.text = currentMessage.message

        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        // if the uid od the current message matches with the uid of teh sender uid :
        // inflate sent view
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND;
        }
        else{
            return ITEM_RECEIVE
        }

    }

    // one viewHolder to receive the message:
    class sentViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txtSentMessage)
    }
    // second viewHolder to receice the message:
    class receiveViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txtReceivedMessage)
    }

}