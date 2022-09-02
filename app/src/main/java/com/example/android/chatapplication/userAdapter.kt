package com.example.android.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class userAdapter(val context:Context, val userList:ArrayList<user>):
    RecyclerView.Adapter<userAdapter.userViewHolder>() {

    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txtName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return userViewHolder(view)
    }
    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        // bind the things with the txtView:
        val currnetUser = userList[position]
        holder.textName.text = currnetUser.name

        // send the user from main activity to the chat window
        // send the name of the user as well as the uid
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatActivity::class.java )
            // send some activity to the chat activity :
            intent.putExtra("name", currnetUser.name)
            intent.putExtra("uid", currnetUser.UID)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}