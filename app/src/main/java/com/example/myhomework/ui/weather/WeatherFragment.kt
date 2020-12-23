package com.example.myhomework.ui.weather

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.myhomework.MainActivity2
import com.example.myhomework.R
import com.example.myhomework.Weather.City
import kotlinx.android.synthetic.main.activity_main2.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.cities.observe(viewLifecycleOwner, Observer {
            val cities=it
            val adapter = activity?.let { it1 -> ArrayAdapter<City>(it1, android.R.layout.simple_list_item_1, cities) }
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                val cityCode = cities[position].city_code
                val intent = Intent(activity, MainActivity2::class.java)
                intent.putExtra("city_code", cityCode)
                Log.d("fragment", "$cityCode")
                startActivity(intent)
            }

        })
    }

}