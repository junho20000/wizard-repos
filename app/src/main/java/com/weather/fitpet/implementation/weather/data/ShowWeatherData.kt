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

class ShowWeatherData(
    var city: String = "",
    var dt_txt: String = "",
    var id: Int = 0,
    var description: String = "",
    var icon: String = "",
    var temp_min: String = "",
    var temp_max: String = ""
) {
    fun init(data: ShowWeatherData) {
        city  = data.city
        dt_txt = data.dt_txt
        id = data.id
        description  = data.description
        icon = data.icon
        temp_min = data.temp_min
        temp_max = data.temp_max
    }
}