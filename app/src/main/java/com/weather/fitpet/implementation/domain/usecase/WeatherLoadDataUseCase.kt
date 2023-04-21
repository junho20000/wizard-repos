package com.weather.fitpet.implementation.domain.usecase

import com.google.gson.Gson
import com.weather.fitpet.base.data.Result
import com.weather.fitpet.base.data.remote.NetworkRepository
import com.weather.fitpet.base.usecase.IOUseCase
import com.weather.fitpet.implementation.domain.model.WeatherRequest
import com.weather.fitpet.implementation.weather.data.WeatherListData
import javax.inject.Inject

/******************************************************************************
 * 업 무 명    : 날씨 데이터 조회 UseCase
 * 파 일 명    : com.weather.fitpet.implementation.domain.usecase.WeatherLoadDataUseCase
 * 설 명      : 날씨에 필요한 정보를 조회 및 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호     2023-04-20           최초작성
 ******************************************************************************/

class WeatherLoadDataUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : IOUseCase<WeatherRequest, String>() {

    public override suspend fun execute(parameter: WeatherRequest): Result<String> {
        return Result.Success(String.toString())
    }

    /**
     * 도시 정보를 이용하여 날씨 정보를 획득
     *
     * @param lat 위도
     * @param lon 경도
     */
    suspend fun loadWeather(lat: Double, lon: Double): WeatherListData? {
        try {
            val weather: String? = networkRepository.requestWeather(lat, lon)
            if (weather != null) {
                return Gson().fromJson(weather, WeatherListData::class.java)
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }
}