package com.weather.fitpet.base.data.entity

import java.io.InputStream

/******************************************************************************
 * 업 무 명    : Response Value 정의
 * 파 일 명    : com.weather.fitpet.base.data.entity.ResponseEntity
 * 설 명      : 서버에서 전달받은 데이터 Entity
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

data class ResponseEntity(
    var _retCode: String? = null,
    var _tran_cd: String? = null,
    var code: Int? = null,
    var serviceData: String? = null,
    var inputStream: InputStream? = null,
    var errorCode: String? = null,
    var errorMessage: String? = null,
    var throwable: Throwable? = null
)