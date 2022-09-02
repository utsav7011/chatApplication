package com.example.android.chatapplication

class user {
    var name:String?= null;
    var email:String? = null;
    var UID:String? = null;

    constructor(){}

    constructor(name:String?, email:String?, uid:String){

        this.name = name
        this.email = email
        this.UID = uid
    }
}


