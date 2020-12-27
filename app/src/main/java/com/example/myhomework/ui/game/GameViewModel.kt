package com.example.myhomework.ui.game

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhomework.cardModel.CardMatchingGame
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private var _game : MutableLiveData<CardMatchingGame> = MutableLiveData()
    private var rgame=loadData()
    fun get(): LiveData<CardMatchingGame> {
        if (rgame != null) {
            _game.postValue(rgame)
        }else{
            _game.postValue( CardMatchingGame(24))
        }
        return _game
    }
    val game: LiveData<CardMatchingGame> = get()
    //保存数据
    fun saveData() {
        try {
            val output = getApplication<Application>()?.openFileOutput(gameFile, AppCompatActivity.MODE_PRIVATE)
            ObjectOutputStream(output).use {
                it.writeObject(gameFragment.mgame)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //加载数据
    fun loadData(): CardMatchingGame? {
        try {
            val input = getApplication<Application>()?.openFileInput(gameFile)
            val objectInputStream =  ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input?.close()
            return game
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}