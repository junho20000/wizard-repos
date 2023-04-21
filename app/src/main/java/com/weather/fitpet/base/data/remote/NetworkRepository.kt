package com.weather.fitpet.base.data.remote

/******************************************************************************
 * 업 무 명    : NetworkRepository Interface
 * 파 일 명    : com.weather.fitpet.base.data.remote.NetworkRepository
 * 설 명      : CachingRepository가 제공하는 함수목록을 정의한다. (CachingRepositoryImpl에서 구현한다.)
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호      2023-04-20           최초작성
 ******************************************************************************/

interface NetworkRepository {
    suspend fun requestGeocoding(cityName: String) : String
    suspend fun requestWeather(lat: Double, lon: Double): String
}