package com.weather.fitpet.base.common.network

import retrofit2.HttpException

/******************************************************************************
 * 업 무 명    : BaseError
 * 파 일 명    : com.weather.fitpet.base.common.network.BaseError
 * 설 명      : 기본 통신 오류 정의
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

sealed class BaseError : Throwable {
    constructor() : super(null, null)

    /**
     * 1. Network 관련 에러
     */
    sealed class Network : BaseError() {

        abstract val code: String

        /**
         * 1-1. HTTP 상태 코드 관련 에러
         */
        sealed class Http : Network() {

            abstract val status: Int
            override val code: String
                get() {
                    return "NT$status"
                }

            /**
             * 그 외
             */
            data class Unknown(override val status: Int) : Http()

            companion object {
                /**
                 * HTTP 상태코드에 해당하는 Http 에러를 얻어온다.
                 *
                 * @param code code
                 * @return BaseError.Network.Http
                 */
                fun parseHttpError(status: Int): Http {
                    return Http::class.getSealedChildClasses()
                            .map { it as? Http }
                            .firstOrNull { it?.status == status } ?: Unknown(status)
                }
            }
        }

        /**
         * 1-2. Connection 관련 에러
         */
        sealed class Connection : Network() {
            object SSLException            : Connection() { override val code: String = "NT001" }
            object ConnectTimeoutException : Connection() { override val code: String = "NT002" }
            object InterruptedIOException  : Connection() { override val code: String = "NT003" }
            object SocketTimeoutException  : Connection() { override val code: String = "NT004" }
            object SocketException         : Connection() { override val code: String = "NT005" }
            object ConnectException        : Connection() { override val code: String = "NT006" }
            object UnknownHostException    : Connection() { override val code: String = "NT007" }
            object UnknownException        : Connection() { override val code: String = "NT008" }
        }
    }

    interface Callback {
        fun onBaseError(e: BaseError)
    }

    companion object {
        /**
         * Throwable에 해당하는 BaseError를 얻어온다.
         *
         * @param e Throwable
         * @return BaseError
         */
        fun parseBaseError(e: Throwable) : BaseError {
            if(e is HttpException) {
                return Network.Http.parseHttpError(e.code())
            }

            val cause = e.cause
            return if(cause != null) {
                when(cause) {
                    is java.net.SocketException           -> Network.Connection.SocketException
                    is java.net.UnknownHostException      -> Network.Connection.UnknownHostException
                    is javax.net.ssl.SSLException         -> Network.Connection.SSLException
                    is java.io.InterruptedIOException     -> Network.Connection.InterruptedIOException
                    else                                  -> parseBaseError(cause)
                }
            } else {
                when(e) {
                    is java.net.SocketException                     -> Network.Connection.SocketException
                    is java.net.UnknownHostException                -> Network.Connection.UnknownHostException
                    is javax.net.ssl.SSLException                   -> Network.Connection.SSLException
                    is java.io.InterruptedIOException               -> Network.Connection.InterruptedIOException
                    is org.apache.http.conn.ConnectTimeoutException -> Network.Connection.ConnectTimeoutException
                    is java.net.SocketTimeoutException              -> Network.Connection.SocketTimeoutException
                    is java.net.ConnectException                    -> Network.Connection.ConnectException
                    else                                            -> Network.Connection.UnknownException
                }
            }
        }
    }
}