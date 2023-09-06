package com.streamhelper.microservices.game.draw.expansions.retrofit

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter

/**
 * 기본적인 필터 처리 및 데이터 추출을 수행합니다.
 * data 클래스만 처리할 수 있습니다.
 */
internal fun Any.asSequencePair() =
    if (this::class.isData.not()) throw IllegalArgumentException("Only Kotlin data class can be request parameters of Retrofit.")
    else this::class.declaredMemberProperties
        .asSequence()
        .filterNot { it.isOpen } // data class 에서 open 속성의 필드는 상속받은 필드 뿐이다. 제외한다.
        .filterNot { it.javaGetter?.modifiers == null } // 접근제한자가 없는 필드는 제외한다.
        .filterNot { it.getter.call(this) == null } // 값이 null 인 필드는 제외한다.
        .map { it.name to it.getter.call(this)!! }