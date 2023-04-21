package com.weather.fitpet.base.common.network

import kotlin.reflect.KClass

/**
 * Sealed Class 하위 object class 전체를 리턴한다.
 *
 * @return
 */

fun KClass<*>.getSealedChildClasses() : List<Any> {
    val mutableList = mutableListOf<Any>()

    sealedSubclasses.forEach { subClass ->
        when {
            subClass.isSealed -> { mutableList.addAll(subClass.getSealedChildClasses()) }
            subClass.isFinal -> { subClass.objectInstance?.let { mutableList.add(it) } }
        }
    }

    return mutableList
}