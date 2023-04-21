package com.weather.fitpet.implementation.weather.data

/******************************************************************************
 * 업 무 명    : 좌표 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.GeocodingData
 * 설 명      : 도시 좌표 정보 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class GeocodingData(
    // 도시이름
    var city: String = "",
    // 위도
    var lat: Double = 0.0,
    // 경도
    var lon: Double = 0.0
) {
    fun init(data: GeocodingData) {
        city = data.city
        lat  = data.lat
        lon  = data.lon
    }
}