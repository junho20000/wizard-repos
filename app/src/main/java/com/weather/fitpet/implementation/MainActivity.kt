package com.weather.fitpet.implementation

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.fitpet.R
import com.weather.fitpet.databinding.ActivityMainBinding
import com.weather.fitpet.implementation.weather.WeatherViewModel
import com.weather.fitpet.implementation.weather.data.ShowWeatherData
import com.weather.fitpet.implementation.weatherlist.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val viewModel by viewModels<WeatherViewModel>()

    private val binding get() = _binding!!

    private val items = MutableLiveData<ArrayList<ShowWeatherData>>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        binding.weatherViewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        searchGeocoding()
        var btnWeather : Button = findViewById(R.id.btn_weather)
        btnWeather.setOnClickListener {
            searchWeather()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val dataObserver: Observer<ArrayList<ShowWeatherData>> = Observer {
            items.value = it
            val adapter = WeatherAdapter(items, this)
            binding.recyclerView.adapter = adapter
        }
        viewModel.itemList.observe(this, dataObserver)
    }

    private fun searchGeocoding() {
        viewModel.initGeocoding()
        viewModel.requestGeocoding("Seoul")
        //viewModel.requestGeocoding("London")
        //viewModel.requestGeocoding("Chicago")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun searchWeather() {
        var geocodingDataList = viewModel.getGeocoding()
        viewModel.initWeather()
        for (geocodingData in geocodingDataList){
            if (geocodingData != null) {
                when (geocodingData.city) {
                    "Seoul" -> viewModel.requestWeatherSeoul(geocodingData.lat, geocodingData.lon)
                    "London" -> viewModel.requestWeatherLondon(geocodingData.lat, geocodingData.lon)
                    "Chicago" -> viewModel.requestWeatherChicago(geocodingData.lat, geocodingData.lon)
                }
            }
        }
    }
}