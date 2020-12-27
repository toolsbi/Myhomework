package com.example.myhomework.ui.watch

import android.app.Application
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhomework.cardModel.CardMatchingGame
import com.example.myhomework.ui.game.gameFile
import com.example.myhomework.ui.game.gameFragment
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class WatchViewModel(application: Application) : AndroidViewModel(application) {
    private var _seconds: MutableLiveData<Int> = MutableLiveData()
    private var running=false
    var second=loadData()
    fun get(): LiveData<Int> {
        if (second != null) {
            _seconds.postValue(second)
        }else{
            _seconds.postValue(0)
        }
        return _seconds
    }
    val seconds: LiveData<Int> = get()
    init {
        runTimer()
    }
    fun start(){
        running=true
    }
    fun stop(){
        running=false
    }
    fun restart(){
        running=true
        _seconds.value=0
    }
    fun runTimer(){
        val handler = Handler()
        val runnable =object:Runnable{
            override fun run() {
                if(running){
                    val sec=_seconds.value?:0
                    _seconds.value=sec+1
                }
                handler.postDelayed(this,1000)
            }

        }
        handler.post(runnable)
    }
    //保存数据
    fun saveData() {
        try {
            val output = getApplication<Application>()?.openFileOutput("WatchFile", AppCompatActivity.MODE_PRIVATE)
            ObjectOutputStream(output).use {
                it.writeObject(WatchFragment.second)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //加载数据
    fun loadData(): Int? {
        try {
            val input = getApplication<Application>()?.openFileInput("WatchFile")
            val objectInputStream =  ObjectInputStream(input)
            val second = objectInputStream.readObject() as Int
            objectInputStream.close()
            input?.close()
            return second
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}