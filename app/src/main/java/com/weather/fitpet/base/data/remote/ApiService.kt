package com.weather.fitpet.base.data.remote

import okhttp3.FormBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import javax.inject.Singleton

/******************************************************************************
 * 업 무 명    : Retrofit Interface
 * 파 일 명    : com.weather.fitpet.base.data.remote.ApiService
 * 설 명      : Retrofit Builder에 들어가는 인터페이스
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 *                                    coroutine 처리를 위해 suspend 함수로 변경
 ******************************************************************************/

interface ApiService {
    /**
     * Retrofit 함수. 코루틴으로 호출 (POST 방식)
     *
     * @param url url
     * @param fields body
     * @return Response<ResponseBody>
     */
    @POST
    suspend fun sendBodyRequest(@Url url: String, @Body fields: FormBody) : Response<ResponseBody>

    /**
     * Retrofit 함수. 코루틴으로 호출 (GET) 방식
     *
     * @param url url
     * @return Response<ResponseBody>
     */
    @GET
    suspend fun sendUrlRequest(@Url url: String) : Response<ResponseBody>
}