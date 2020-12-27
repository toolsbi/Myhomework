package com.example.myhomework.ui.Music

import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhomework.MainActivity
import com.example.myhomework.R

class MusicViewModel(application: Application): AndroidViewModel(application) {
    var current =0
        get() = field
    var isPause=true
        get() = field
    val musicList= mutableListOf<String>()
    val musicNameList= mutableListOf<String>()
    fun getMusicList(){
        val cursor=getApplication<Application>().contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if(cursor!=null){
            while(cursor.moveToNext()){
                val musicPath=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                val musicName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(ContentValues.TAG,"getMusicList:$musicPath name:$musicName")
            }
            cursor.close()
        }
    }
    fun notification() {
        val Channel_ID="my channel"
        val Notification_ID=1
        val notificationManager=getApplication<Application>().getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        val builder: Notification.Builder
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel= NotificationChannel(Channel_ID,"test", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
            builder= Notification.Builder(getApplication<Application>().applicationContext,Channel_ID)
        }else{
            builder= Notification.Builder(getApplication<Application>().applicationContext)
        }

        val intent= Intent(getApplication<Application>().applicationContext, MusicFragment::class.java)
        //延迟意图
        val pendingIntent= PendingIntent.getActivity(getApplication<Application>().applicationContext,1,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification=builder.setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("新音乐播放通知")
            .setContentText("当前${musicNameList[current]}正在播放!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(Notification_ID,notification)
        isPause=true
    }
    fun setOnCompletionListener() {
        current++
        if (current >= musicList.size-1) {
            current = 0
        }
    }
    fun onNext(){
        current++
        if(current>=musicList.size){
            current=0
        }
    }
    fun onPrev(){
        current--
        if(current<0){
            current = musicList.size-1
        }
    }
}