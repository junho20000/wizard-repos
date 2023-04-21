package com.weather.fitpet.base.common.di.module

import com.weather.fitpet.base.data.remote.NetworkRepository
import com.weather.fitpet.base.data.remote.NetworkRepositoryImpl
import com.weather.fitpet.base.data.remote.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/******************************************************************************
 * 업 무 명    : Repository 의존성주입
 * 파 일 명    : com.weather.fitpet.base.common.di.module.RepositoryModule
 * 설 명      : Data Layer의 Repository를 생성하여 제공한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * NetworkRepository DI
     *
     * @param retrofitService RetrofitService

     * @return CachingRepository
     */
    @Singleton
    @Provides
    fun provideNetworkRepository(
        retrofitService: RetrofitService
    ) : NetworkRepository {
        return NetworkRepositoryImpl(
            retrofitService,
        )
    }
}