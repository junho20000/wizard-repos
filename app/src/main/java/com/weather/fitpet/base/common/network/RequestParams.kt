package com.weather.fitpet.base.common.network

import android.os.Bundle
import androidx.core.os.bundleOf
import okhttp3.FormBody
import org.json.JSONObject
import java.io.Serializable
import java.nio.charset.Charset

/******************************************************************************
 * 업 무 명    : 통신 파라미터
 * 파 일 명    : com.weather.fitpet.base.common.network.RequestParams
 * 설 명      : 통신을 위한 Parameter
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1   노준호     2023-04-20           최초작성
 ******************************************************************************/

sealed class RequestParams : Serializable {
    abstract val nameValuePairList: ArrayList<NameValuePair>

    /**
     * 일반 Http Request Parameter Model
     *
     * @property nameValuePairList NameValuePairList
     */
    data class HttpParams(
        override val nameValuePairList: ArrayList<NameValuePair> = arrayListOf()
    ) : RequestParams()

    /**
     * 파라미터 추가
     *
     * @param name name
     * @param value value
     */
    fun put(name: String, value: String) {
        nameValuePairList.add(
            NameValuePair(
                name,
                value
            )
        )
    }

    /**
     * name이 중복될 수 있어서 마지막으로 저장된 name을 추출
     *
     * @param name name
     * @return NameValuePair
     */
    operator fun get(name: String): NameValuePair? {
        return nameValuePairList.lastOrNull { pair ->
            pair.name == name
        }
    }

    fun getValue(key: String): String? {
        return nameValuePairList.lastOrNull { pair ->
            pair.name == key
        }?.value
    }

    /**
     * index 로 NameValuePair 추출
     *
     * @param index index
     * @return NameValuePair
     */
    fun get(index: Int): NameValuePair? {
        return nameValuePairList.elementAtOrNull(index)
    }

    /**
     * name 또는 value가 포함되어 있는지 확인
     * @param str str
     * @return 포함여부
     */
    fun contains(str: String) : Boolean {
        return nameValuePairList.any { pair ->
            pair.name.contains(str) || pair.value.contains(str)
        }
    }

    /**
     * 통신을 위한 formBody로 변환
     *
     * @return FormBody
     */
    fun getFormBody() : FormBody {
        val builder = FormBody.Builder(Charset.forName("utf-8"))

        nameValuePairList.forEach { pair ->
            builder.add(pair.name, pair.value)
        }

        return builder.build()
    }

    data class NameValuePair(
        val name: String,
        val value: String
    ) : Serializable
}