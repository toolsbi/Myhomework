package com.example.myhomework.ui.game

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomework.cardModel.CardMatchingGame
import com.example.myhomework.R
import com.example.myhomework.cardModel.CardRecyclerViewAdapter

const val  gameFile="gameFile"
class gameFragment : Fragment() {
    companion object {
        lateinit var mgame: CardMatchingGame
    }
    private lateinit var viewModel: GameViewModel
    lateinit var adapter: CardRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.game.observe(viewLifecycleOwner, Observer {
            mgame=it
            val lgame=it
            adapter = CardRecyclerViewAdapter(lgame)
            val recylerView = view.findViewById<RecyclerView>(R.id.recylerView2)
            val reset = view.findViewById<Button>(R.id.reset)
            recylerView.adapter = adapter
            val configuration = resources.configuration
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recylerView.layoutManager = GridLayoutManager(activity, 6)
            }else{
                val gridLayoutManager = GridLayoutManager(activity, 4)
                recylerView.layoutManager = gridLayoutManager
            }
            val score = view?.findViewById<TextView>(R.id.score)

            adapter.notifyDataSetChanged()
            score?.text = String.format("%s%d",getString(R.string.score),lgame.score)
            score?.text = getString(R.string.score) + lgame.score

            adapter.setOnCardClickListener {
                lgame.chooseCardAtIndex(it)
                val score = view?.findViewById<TextView>(R.id.score)

                adapter.notifyDataSetChanged()
                score?.text = String.format("%s%d",getString(R.string.score),lgame.score)
                score?.text = getString(R.string.score) + lgame.score
            }

            reset.setOnClickListener {
                lgame.reset()
                val score = view?.findViewById<TextView>(R.id.score)

                adapter.notifyDataSetChanged()
                score?.text = String.format("%s%d",getString(R.string.score),lgame.score)
                score?.text = getString(R.string.score) + lgame.score
            }
        })
    }
    override fun onStop() {
        super.onStop()
        viewModel.saveData()
    }




}