package com.weather.fitpet.base.data

import com.weather.fitpet.base.common.network.BaseError

/******************************************************************************
 * 업 무 명    : Result
 * 파 일 명    : com.weather.fitpet.base.data.Result
 * 설 명      : data, domain layer 에서, 결과값을 반환할때 Result로 래핑하여 전달한다.
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호    2023-04-20           최초작성
 ******************************************************************************/

sealed class Result<out R> {

    /**
     * 성공에 대한 Response Result
     *
     * @param T Response Class
     * @property data Response Data
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * 실패에 대한 Response Result
     *
     * @property error BaseError
     */
    data class Error(val error: BaseError) : Result<kotlin.Nothing>()

    /**
     * 아무것도 하지 않을 경우의 Response Result
     */
    object Nothing : Result<kotlin.Nothing>()

    /**
     * toString Override
     *
     * @return String
     */
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$error]"
            is Nothing -> "Nothing"
        }
    }
}