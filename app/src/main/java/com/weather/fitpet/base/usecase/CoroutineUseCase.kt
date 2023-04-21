package com.weather.fitpet.base.usecase

import com.weather.fitpet.base.common.network.BaseError
import com.weather.fitpet.base.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/******************************************************************************
 * 업 무 명    : Coroutine UseCase
 * 파 일 명    : com.weather.fitpet.base.usecase.CoroutineUseCase
 * 설 명      : Coroutine을 사용하여 비동기 실행하는 경우, CoroutineUseCase를 상속받아 구현한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

abstract class CoroutineUseCase<in PARAMETER, RESPONSE>(
    private val dispatcher: CoroutineDispatcher
) : BaseUseCase<PARAMETER, Result<RESPONSE>>() {
    /**
     * 코루틴 Dispatcher 로 UseCase 를 비동기로 실행한다.
     *
     * @param parameter 파라미터
     * @return Response
     */
    override suspend fun invoke(parameter: PARAMETER): Result<RESPONSE> {
        return try {
            withContext(dispatcher) {
                execute(parameter)
            }
        } catch (e: BaseError) {
            Timber.e("[E<${e.javaClass.simpleName}>] - ${e.printStackTrace()}")
            Result.Error(e)
        }
    }
}