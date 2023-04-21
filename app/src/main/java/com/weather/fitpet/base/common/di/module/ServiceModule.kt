package com.weather.fitpet.base.common.di.module

import com.weather.fitpet.base.common.network.HttpInterceptor
import com.weather.fitpet.base.data.remote.ApiService
import com.weather.fitpet.base.data.remote.RetrofitService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/******************************************************************************
 * 업 무 명    : Service 클래스 의존성주입
 * 파 일 명    : com.weather.fitpet.base.common.di.module.ServiceModule
 * 설 명      : Data Layer의 Service 클래스들을 생성하여 제공한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    /**
     * ConnectionPool DI
     *
     * @return ConnectionPool
     */
    @Singleton
    @Provides
    fun provideConnectionPool() : ConnectionPool {
        return ConnectionPool(5, 10, TimeUnit.SECONDS)
    }

    /**
     * OkHttpClient DI
     *
     * @param connectionPool ConnectionPool
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        connectionPool: ConnectionPool
    ) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .hostnameVerifier({ _, _ -> true })
            .connectTimeout(TIMEOUT_SECONDS_CO, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT_SECONDS_SO, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS_WRITE, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .connectionPool(connectionPool)
            .addInterceptor(HttpInterceptor)
            .build()
    }

    /**
     * Retrofit Interface DI
     *
     * @param localRepository LocalRepository
     * @param okHttpClient OkHttpClient
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient
    ) : ApiService {
        val baseUrl = "http://api.openweathermap.org/"

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * RetrofitService DI
     *
     * @param apiService ApiService
     * @param connectionPool ConnectionPool
     * @return RetrofitService
     */
    @Provides
    @Singleton
    fun provideRetrofitService(
        apiService: ApiService,
        connectionPool: ConnectionPool
    ) : RetrofitService {
        return RetrofitService(
            apiService,
            connectionPool
        )
    }

    private const val TIMEOUT_SECONDS_CO = 10L  // Connection Timeout 10초
    private const val TIMEOUT_SECONDS_SO = 60L  // Socket Timeout 60초
    private const val TIMEOUT_SECONDS_READ = 60L    // 서버로 부터 응답까지 시간 60초
    private const val TIMEOUT_SECONDS_WRITE = 60L   // 서버로 데이터 전송 시간 60초
}