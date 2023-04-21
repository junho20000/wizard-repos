package com.weather.fitpet.base.data.remote

import android.os.Build
import com.weather.fitpet.base.common.network.BaseError
import com.weather.fitpet.base.data.entity.RequestEntity
import com.weather.fitpet.base.common.network.HttpInterceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ConnectionPool
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/******************************************************************************
 * 업 무 명    : 통신 클래스
 * 파 일 명    : com.weather.fitpet.base.data.remote.RetrofitService
 * 설 명      : Retrofit & OkHttpClient & Coroutine 을 사용해 서버로 데이터를 보내거나 받는다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 *                                    Retrofit / OkHttpClient 객체 DI에서 제공하도록 수정
 *                                    coroutine 호출로 변경
 *                                    requestPost 함수로 통일
 ******************************************************************************/

class RetrofitService @Inject constructor(
    private val mApiService: ApiService,
    private val mConnectionPool: ConnectionPool
) {

    private val fitpetUserAgent : String by lazy {
        String.format(FITPET_USER_AGENT, PLATFORM, Build.VERSION.RELEASE, Build.MODEL)
    }

    private val mHeaders: Headers by lazy {
        Headers.Builder()
            .add(USER_AGENT, fitpetUserAgent)
            .add(CONNECTION, KEEP_ALIVE)
            .build()
    }

    private var mUrl: String = ""

    /**
     * HTTP GET 방식으로 서버에 데이터를 요청한다.
     *
     * @param url url
     * @return Response<ResponseBody>
     */
    suspend fun requestGet(url: String): Response<ResponseBody> = withContext(Dispatchers.IO) {
        try {
            return@withContext flow {
                emit(mApiService.sendUrlRequest(url))
            }.retryWhen { e, attempt ->
                return@retryWhen false
            }.catch { e ->
                throw BaseError.parseBaseError(e)
            }.map { response ->
                mConnectionPool.evictAll()

                if(response.code() == 200) {
                    response
                } else {
                    throw HttpException(response)
                }
            }.first()

        } catch (e: BaseError) {
            Timber.e("[E<${e.javaClass.simpleName}>] - ${e.printStackTrace()}")
            throw e

        } catch (e: Exception) {
            Timber.e("[E<${e.javaClass.simpleName}>] - ${e.printStackTrace()}")
            throw BaseError.parseBaseError(e)
        }
    }

    /**
     * HTTP POST 방식으로 서버에 데이터를 요청한다.
     *
     * @param requestEntity RequestEntity
     * @return ResponseEntity
     */
    suspend fun requestPost(requestEntity: RequestEntity): Response<ResponseBody> = withContext(Dispatchers.IO) {
        try {
            with(requestEntity) {
                mUrl = url
                HttpInterceptor.setHeader(mHeaders)

                return@withContext flow {
                    emit(mApiService.sendBodyRequest(url, params.getFormBody()))
                }.retryWhen { e, attempt ->
                    return@retryWhen false
                }.catch { e ->
                    throw BaseError.parseBaseError(e)
                }.map { response ->
                    mConnectionPool.evictAll()

                    if(response.code() == 200) {
                        response
                    } else {
                        throw HttpException(response)
                    }
                }.first()
            }

        } catch (e: BaseError) {
            Timber.e("[E<${e.javaClass.simpleName}>] - ${e.printStackTrace()}")
            throw e

        } catch (e: Exception) {
            Timber.e("[E<${e.javaClass.simpleName}>] - ${e.printStackTrace()}")
            throw BaseError.parseBaseError(e)
        }
    }

    /**
     * UserAgent를 얻어온다.
     *
     * @return
     */
    /*fun getFitpetUserAgent() : String {
        return fitpetUserAgent
    }*/

    companion object {
        const val PLATFORM = "Android"
        private const val USER_AGENT = "User-Agent"
        private const val CONNECTION = "Connection"
        private const val KEEP_ALIVE = "keep-alive"
        private const val FITPET_USER_AGENT = "Delfino-fitpet/%s|%s|%s"
    }
}