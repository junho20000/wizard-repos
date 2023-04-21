package com.weather.fitpet.implementation.weather.data

/******************************************************************************
 * 업 무 명    : 날씨 메인 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.MainData
 * 설 명      : 날씨 메인 정보 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class MainData(
    var temp: String = "",
    var feels_like: Double = 0.0,
    var temp_min: Double = 0.0,
    var temp_max: Double = 0.0,
    var pressure: Int = 0,
    var sea_level: Int = 0,
    var grnd_level: Int = 0,
    var humidity: Int = 0,
    var temp_kf: Double = 0.0
) {
    fun init(data: MainData) {
        temp = data.temp
        feels_like  = data.feels_like
        temp_min  = data.temp_min
        temp_max = data.temp_max
        pressure = data.pressure
        sea_level = data.sea_level
        grnd_level = data.grnd_level
        humidity = data.humidity
        temp_kf = data.temp_kf
    }
}