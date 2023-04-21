package com.weather.fitpet.base.data.remote

import com.weather.fitpet.base.common.constant.Define
import com.weather.fitpet.base.common.network.BaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

/******************************************************************************
 * 업 무 명    : NetworkRepository
 * 파 일 명    : com.weather.fitpet.base.data.remote.NetworkRepositoryImpl
 * 설 명      : 서버에 데이터를 요청하는 함수를 제공한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 *                                    Repository 함수 제공 패턴으로 변경
 ******************************************************************************/

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService,
): NetworkRepository {
    /**
     * 도시의 위,경도값 호출
     * 파라미터를 조합하고, 서버에 요청한다.
     *
     * @param cityName 도시 이름
     * @return 리턴값
     */
    override suspend fun requestGeocoding(cityName: String): String = withContext(Dispatchers.IO) {
        try {
            val url = Define.URL_GEOCODING + "direct?q=${cityName}&limit=1&appid=" + Define.OPEN_API_KEY

            val response = retrofitService.requestGet(url)
            val responseString = getResponseData(response)

            return@withContext responseString
        } catch (e: BaseError) {
            throw e

        } catch (e: Exception) {
            throw BaseError.parseBaseError(e)
        }
    }

    /**
     * 도시의 날씨 호출
     * 파라미터를 조합하고, 서버에 요청한다.
     *
     * @param cityName 도시 이름
     * @return 리턴값
     *
     * @return 리턴값
     */
    override suspend fun requestWeather(lat: Double, lon: Double): String = withContext(Dispatchers.IO) {
        try {
            Timber.d("[Step_requestWeather] requestWeather")

            val url = "http://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=f14dab485a3712ab711c5725b46b6431"

            val response = retrofitService.requestGet(url)
            val responseString = getResponseData(response)
            Timber.d("[Step_requestWeather] requestWeather - responseString : $responseString")

            return@withContext responseString
        } catch (e: BaseError) {
            throw e

        } catch (e: Exception) {
            throw BaseError.parseBaseError(e)
        }
    }

    /**
     * 네트워크 ResponseBody ResponseEntity로 변환
     *
     * @param response Response
     * @return ResponseEntity
     */
    private fun getResponseData(response: Response<ResponseBody>) : String {
        try {
            val responseBody = response.body()
            if (responseBody !is ResponseBody) {
                throw Exception("HTTP entity may not be null")
            }

            responseBody.let { body ->
                if (body.contentLength() > Integer.MAX_VALUE) {
                    throw Exception("HTTP entity too large to be buffered in memory")
                }
                return body.string()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}