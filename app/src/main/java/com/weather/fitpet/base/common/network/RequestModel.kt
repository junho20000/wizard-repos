package com.weather.fitpet.base.common.network

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/*****************************************************************************
 * 업 무 명    : Request Model 에 대한 정의
 * 파 일 명    : com.weather.fitpet.base.common.network.RequestModel
 * 설 명      : 요청 데이터를 모델화하기 위해 사용한다.
 ************************** 변 경 이 력 *****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * ****************************************************************************
 *  1   정역화                          최초작성
 ******************************************************************************/

@Parcelize
data class RequestModel(
    val urlParams: String? = null,                              // url 요청 params
    val bodyParams: RequestParams = RequestParams.HttpParams()  // url 요청 body params - 페이지에 전달할 파라미터
) : Serializable, Parcelable