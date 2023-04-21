package com.weather.fitpet.implementation.weather.data

/******************************************************************************
 * 업 무 명    : 날씨 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.WeatherData
 * 설 명      : 날씨 정보 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class WeatherData(
    var id: Int = 0,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
) {
    fun init(data: WeatherData) {
        id = data.id
        main  = data.main
        description  = data.description
        icon = icon
    }
}