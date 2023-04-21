package com.weather.fitpet.implementation.weather.data

/******************************************************************************
 * 업 무 명    : 날씨 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.ListData
 * 설 명      : 리스트에 표시될 날씨에 필요한 정보를 조회 및 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class ListData(
    var main: MainData,
    var weather: Array<WeatherData> = emptyArray(),
    var visibility: Int = 0,
    var pop: Double,
    var dt_txt: String = ""
) {
    fun init(data: ListData) {
        main = data.main
        weather = data.weather
        visibility = data.visibility
        pop = data.pop
        dt_txt = data.dt_txt
    }
}