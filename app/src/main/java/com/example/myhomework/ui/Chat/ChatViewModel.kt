package com.example.myhomework.ui.Chat

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class ChatViewModel(application: Application):AndroidViewModel(application) {
    private  val _msgList:MutableLiveData<MutableList<ChatFragment.Msg>> = MutableLiveData()
    val msgs = loadData()
    fun getmsgList() : LiveData<MutableList<ChatFragment.Msg>>{
        if (msgs == null)
        {
            _msgList.postValue(mutableListOf())
        }else {
            _msgList.postValue(msgs)
        }
        return _msgList
    }
    val msgList: LiveData<MutableList<ChatFragment.Msg>> = getmsgList()
    fun loadData():MutableList<ChatFragment.Msg>? {
        try {
            val input = getApplication<Application>().openFileInput("data")
            val objectInputStream = ObjectInputStream(input)

            val msgs = objectInputStream.readObject() as MutableList<ChatFragment.Msg>

            objectInputStream.close()
            input?.close()
            return msgs

        }catch (e: Exception){
            return null
        }


    }
}