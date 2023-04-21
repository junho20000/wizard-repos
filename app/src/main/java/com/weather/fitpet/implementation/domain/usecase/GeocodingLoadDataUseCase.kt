package com.weather.fitpet.implementation.domain.usecase

import com.weather.fitpet.base.data.Result
import com.weather.fitpet.base.data.remote.NetworkRepository
import com.weather.fitpet.base.usecase.IOUseCase
import javax.inject.Inject

/******************************************************************************
 * 업 무 명    : 도시의 위도,경도 데이터 조회 UseCase
 * 파 일 명    : com.weather.fitpet.implementation.domain.usecase.WeatherLoadDataUseCase
 * 설 명      : 도시의 위도,경도 정보를 조회 및 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호     2023-04-20           최초작성
 ******************************************************************************/

class GeocodingLoadDataUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : IOUseCase<String, String>() {

    public override suspend fun execute(city: String): Result<String> {
        val geocoding: String? = networkRepository.requestGeocoding(city)
        return Result.Success(geocoding.toString())
    }

    /**
     * 도시 좌표 요청
     *
     * @param city 파일명
     */
    suspend fun loadGeocoding(city: String): String {
        try {
            val geocoding: String? = networkRepository.requestGeocoding(city)
            if (!geocoding.isNullOrEmpty()) {
                return geocoding
            }
        } catch (e: Exception) {
            val msg = e.toString()
            return ""
        }
        return ""
    }
}