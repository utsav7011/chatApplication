package com.example.android.chatapplication

//model of message  :

class message {
    var message:String? = null
    var senderId:String? = null
    constructor(){}

    constructor(message:String?, senderId:String?){

        this.message = message
        this.senderId = senderId

    }
}