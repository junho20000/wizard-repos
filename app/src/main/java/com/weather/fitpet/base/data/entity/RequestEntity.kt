package com.weather.fitpet.base.data.entity

import com.weather.fitpet.base.common.network.RequestParams

/******************************************************************************
 * 업 무 명    : Request Value 정의
 * 파 일 명    : com.weather.fitpet.base.data.entity.RequestEntity
 * 설 명      : 서버에 요청할 데이터 Entity
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

data class RequestEntity(
    val url: String,
    val params: RequestParams
)