package com.weather.fitpet.implementation.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.fitpet.implementation.domain.usecase.GeocodingLoadDataUseCase
import com.weather.fitpet.implementation.domain.usecase.WeatherLoadDataUseCase
import com.weather.fitpet.implementation.weather.data.GeocodingData
import com.weather.fitpet.implementation.weather.data.ShowWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/******************************************************************************
 * 업 무 명    : 날씨 ViewModel
 * 파 일 명    : com.weather.fitpet.implementation.weather.WeatherViewModel
 * 설 명      : 날씨 요청 및 화면 갱신 요청기능
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val requestGeocodingUseCase: GeocodingLoadDataUseCase,
    private val requestWeatherUseCase: WeatherLoadDataUseCase
) : ViewModel() {
    // 날씨 정보 UI 갱신 옵져버
    val _itemList = MutableLiveData<ArrayList<ShowWeatherData>>()

    val itemList: LiveData<ArrayList<ShowWeatherData>> get() = _itemList

    // 도시 위도, 경도 정보
    var mGeocodingData = ArrayList<GeocodingData>()

    var mSeoulWeatherData = ArrayList<ShowWeatherData>()
    var mLandonWeatherData = ArrayList<ShowWeatherData>()
    var mChicagoWeatherData = ArrayList<ShowWeatherData>()

    init {
        val recyclerViewItems: ArrayList<ShowWeatherData> = ArrayList()
        _itemList.value = recyclerViewItems
    }

    fun initGeocoding() {
        mGeocodingData.clear()
    }

    fun initWeather() {
        mSeoulWeatherData.clear()
        mLandonWeatherData.clear()
        mChicagoWeatherData.clear()
    }

    fun getGeocoding(): ArrayList<GeocodingData> {
        return mGeocodingData
    }

    /**
     * 도시 좌표 요청
     */
    fun requestGeocoding(city: String) {
        viewModelScope.launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                val geocoding = requestGeocodingUseCase.loadGeocoding(city)
                if (!geocoding.isNullOrEmpty()) {
                    val jsonArray = JSONArray(geocoding)
                    val jsonObject : JSONObject = jsonArray.get(0) as JSONObject
                    val lat = jsonObject.getDouble("lat")
                    val lon = jsonObject.getDouble("lon")

                    Timber.d("responseString : $lat, $lon")

                    var geocodingData = GeocodingData(city, lat, lon)
                    mGeocodingData.add(geocodingData)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestWeatherSeoul(lat: Double, lon: Double) {
        viewModelScope.launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                var weatherListData = requestWeatherUseCase.loadWeather(lat, lon)
                if (weatherListData != null) {
                    var prevTime: LocalDateTime
                    var dspDate = ""
                    for (listData in weatherListData.list) {
                        var weatherData = ShowWeatherData(
                            weatherListData.city.name,
                            listData.dt_txt,
                            listData.weather[0].id,
                            listData.weather[0].description,
                            listData.weather[0].icon,
                            String.format("Min : %.0f °C", (listData.main.temp_min - 273.15) * 9/5),
                            String.format("Max : %.0f °C", (listData.main.temp_min - 273.15) * 9/5))

                        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val parsedDateTime = LocalDateTime.parse(listData.dt_txt, pattern)
                        val nowDate = LocalDateTime.now()
                        if (parsedDateTime.isBefore(nowDate)) {
                            continue
                        } else {
                            prevTime = nowDate
                        }

                        var parsedToken =  listData.dt_txt.split(' ')
                        if (parsedToken[0] == dspDate)
                            continue

                        val cmp = parsedDateTime.compareTo(prevTime)
                        if (cmp > 0 || cmp == 0) {
                            mSeoulWeatherData.add(weatherData)
                            dspDate = parsedToken[0]
                        }
                    }

                    _itemList.postValue(mSeoulWeatherData)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestWeatherLondon(lat: Double, lon: Double) {
        viewModelScope.launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                var weatherListData = requestWeatherUseCase.loadWeather(lat, lon)
                if (weatherListData != null) {
                    var prevTime : LocalDateTime
                    for (listData in weatherListData.list) {
                        var weatherData = ShowWeatherData(
                            weatherListData.city.name,
                            listData.dt_txt,
                            listData.weather[0].id,
                            listData.weather[0].description,
                            listData.weather[0].icon,
                            String.format("Min : %.0f °C", (listData.main.temp_min - 273.15) * 9/5),
                            String.format("Max : %.0f °C", (listData.main.temp_min - 273.15) * 9/5))

                        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val parsedDateTime = LocalDateTime.parse(listData.dt_txt, pattern)
                        val nowDate = LocalDateTime.now()
                        if (parsedDateTime.isBefore(nowDate)) {
                            continue
                        } else {
                            prevTime = nowDate
                        }

                        val cmp = parsedDateTime.compareTo(prevTime)
                        if (cmp > 0 || cmp == 0)
                            mLandonWeatherData.add(weatherData)
                    }

                    _itemList.postValue(mLandonWeatherData)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestWeatherChicago(lat: Double, lon: Double) {
        viewModelScope.launch {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                var weatherListData = requestWeatherUseCase.loadWeather(lat, lon)
                if (weatherListData != null) {
                    var prevTime: LocalDateTime
                    for (listData in weatherListData.list) {
                        var weatherData = ShowWeatherData(
                            weatherListData.city.name,
                            listData.dt_txt,
                            listData.weather[0].id,
                            listData.weather[0].description,
                            listData.weather[0].icon,
                            String.format("Min : %.0f °C", (listData.main.temp_min - 273.15) * 9/5),
                            String.format("Max : %.0f °C", (listData.main.temp_min - 273.15) * 9/5))

                        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val parsedDateTime = LocalDateTime.parse(listData.dt_txt, pattern)
                        val nowDate = LocalDateTime.now()
                        if (parsedDateTime.isBefore(nowDate)) {
                            continue
                        } else {
                            prevTime = nowDate
                        }

                        val cmp = parsedDateTime.compareTo(prevTime)
                        if (cmp > 0 || cmp == 0)
                            mChicagoWeatherData.add(weatherData)
                    }

                    _itemList.postValue(mChicagoWeatherData)
                }
            }
        }
    }
}
