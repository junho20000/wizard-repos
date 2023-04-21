package com.weather.fitpet.base.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/******************************************************************************
 * 업 무 명    : IO UseCase
 * 파 일 명    : com.weather.fitpet.base.usecase.IOUseCase
 * 설 명      : IO작업(네트워크, 로컬)을 수행하는 경우, IOUseCase를 상속받아 구현한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작업자    작업일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

abstract class IOUseCase<in PARAMETER, RESPONSE> (
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineUseCase<PARAMETER, RESPONSE>(ioDispatcher)