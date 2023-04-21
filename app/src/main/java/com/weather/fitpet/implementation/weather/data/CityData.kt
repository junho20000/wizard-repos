package com.weather.fitpet.implementation.weather.data

/******************************************************************************
 * 업 무 명    : 좌표 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.CityData
 * 설 명      : 도시 좌표 정보 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class CityData(
    var id: Int = 0,
    var name: String = "",
    var country: String = "",
    var timezone: String = "",
    var sunrise: Double = 0.0,
    var sunset: Double = 0.0
) {
    fun init(data: CityData) {
        id = data.id
        name  = data.name
        country  = data.country
        timezone = data.timezone
        sunrise = data.sunrise
        sunset = data.sunset
    }
}