package com.weather.fitpet.implementation.weather.data

import org.json.JSONObject

/******************************************************************************
 * 업 무 명    : 날씨 정보
 * 파 일 명    : com.weather.fitpet.implementation.weather.data.WeatherListData
 * 설 명      : 리스트에 표시될 날씨에 필요한 정보를 조회 및 가공 처리
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class WeatherListData(
    var cod: String = "",
    var message: Int = 0,
    var cnt: Int = 0,
    var list: Array<ListData> = emptyArray(),
    var city: CityData
) {
    fun init(data: WeatherListData) {
        cod = data.cod
        message = data.message
        cnt = data.cnt
        list = data.list
        city = data.city
    }
}