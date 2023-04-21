package com.weather.fitpet.base.common.network

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/******************************************************************************
 * 업 무 명    : HttpInterceptor 인터셉터
 * 파 일 명    : com.weather.fitpet.base.common.network.HttpInterceptor
 * 설 명      : OkHttpClient Interceptor
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

object HttpInterceptor : Interceptor {
    private var mUrl: String = ""
    private var mHeaders: Headers? = null

    /**
     * URL 설정
     *
     * @param url url
     */
    fun setUrl(url: String) {
        mUrl = url
    }

    /**
     * Header 설정
     *
     * @param headers Headers
     */
    fun setHeader(headers: Headers) {
        mHeaders = headers
    }

    /**
     * interceptor
     *
     * @param chain chain
     * @return Response
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            var request = chain.request()
            if (mHeaders == null) {
                mHeaders = Headers.Builder().build()
            }

            val newUrl = if (mUrl.isNotEmpty()) {
                request.url.newBuilder(mUrl)?.build()
            } else {
                request.url
            }

            ifLet(newUrl, mHeaders) { url, headers ->
                request = request.newBuilder()
                    .url(url)
                    .headers(headers)
                    .build()
            }

            return chain.proceed(request)
        } catch (e: IOException) {
            throw e
        }
    }
}