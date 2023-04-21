package com.weather.fitpet.base.usecase

/******************************************************************************
 * 업 무 명    : UseCase 최상위 클래스
 * 파 일 명    : com.weather.fitpet.base.usecase.BaseUseCase
 * 설 명      : 모든 UseCase 는 BaseUseCase 를 상속받아 구현한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

abstract class BaseUseCase<in PARAMETER, RESPONSE> {
    /**
     * UseCase 실행 함수
     *
     * @param parameter Parameter
     * @return Response
     */
    protected abstract suspend fun execute(parameter: PARAMETER) : RESPONSE

    /**
     * UseCase invoke 함수
     *
     * @param parameter Parameter
     * @return Response
     */
    abstract suspend operator fun invoke(parameter: PARAMETER) : RESPONSE
}