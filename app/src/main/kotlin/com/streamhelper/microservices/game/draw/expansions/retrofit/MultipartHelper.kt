package com.streamhelper.microservices.game.draw.expansions.retrofit

import okhttp3.MultipartBody
import java.io.File
import kotlin.reflect.KClass

object MultipartHelper {

    interface PartConverter {
        fun toMultipartPart(name: String, value: Any): MultipartBody.Part?
    }

    private val handlers = mutableMapOf<KClass<*>, PartConverter>()

    init {
        addPartHandler(BooleanPartConverter(), Boolean::class)
        addPartHandler(CharPartConverter(), Char::class)
        addPartHandler(NumberPartConverter(), Number::class)
        addPartHandler(StringPartConverter(), String::class)
        addPartHandler(FilePartConverter(), File::class)
    }

    fun addPartHandler(handler: PartConverter, clazz: KClass<*>) {
        handlers[clazz] = handler
    }

    /**
     * 미리 설정된 핸들러를 통해서 value: [Any] 를 [MultipartBody.Part] 로 매핑합니다.
     */
    fun toSequenceParts(data: Any) = data.asSequencePair()
        .map { (name, value) ->
            val requestBody = handlers[value::class]
                ?.toMultipartPart(name, value)
                ?: throw IllegalStateException("Cannot handle type: ${value::class.qualifiedName}")
            name to requestBody
        }

    fun toPartsMap(data: Any) = toSequenceParts(data).toMap()
    fun toPartsList(data: Any) = toSequenceParts(data).toList()
}