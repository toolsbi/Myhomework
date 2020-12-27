package com.example.myhomework.ui.Music

import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.myhomework.R
import kotlinx.android.synthetic.main.fragment_music.*
import java.io.IOException
import kotlin.concurrent.thread

class MusicFragment : Fragment() {

    companion object {
        fun newInstance() = MusicFragment()
    }
    val mediaPlayer= MediaPlayer()
    lateinit var musicList:MutableList<String>
    lateinit var musicNameList:MutableList<String>
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        musicList=viewModel.musicList
        musicNameList=viewModel.musicNameList
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            play()
            viewModel.setOnCompletionListener()
            viewModel.notification()

        }
        if(context?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0) }
        }else{
                viewModel.getMusicList()

        }
        seekBar.setOnSeekBarChangeListener(object :  SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress:Int,
                fromUser:Boolean
            ) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        thread {
            while (true){
                Thread.sleep(1000)
                activity?.runOnUiThread {
                    seekBar.max=mediaPlayer.duration
                    seekBar.progress=mediaPlayer.currentPosition
                }
            }
        }
        button.setOnClickListener {
            play()
        }
        button2.setOnClickListener {
            if(!viewModel.isPause){
                mediaPlayer.start()
            }else{
                mediaPlayer.pause()
                viewModel.isPause = true
            }
        }
        button3.setOnClickListener {
            mediaPlayer.start()
        }
        button4.setOnClickListener {
            viewModel.onNext()
            viewModel.notification()
            play()
        }
        button5.setOnClickListener {
            viewModel.onPrev()
            play()
            viewModel.notification()
        }
    }
    fun play(){
        var current=viewModel.current
        if(musicList.size==0) return
        val path=musicList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            textView_mn.text=musicNameList[current]
            textView_count.text="${current+1}/${musicList.size}"
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            viewModel.getMusicList()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
        viewModel.musicNameList.clear()
        viewModel.musicList.clear()
        viewModel.current=0
    }

}