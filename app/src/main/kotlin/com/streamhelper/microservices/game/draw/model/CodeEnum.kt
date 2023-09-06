package com.streamhelper.microservices.game.draw.model

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

interface CodeEnum<T> {
    val code: T
}

abstract class CodeEnumDeserializer<T, R : CodeEnum<T>> : StdDeserializer<R> {
    constructor(vc: Class<*>?) : super(vc)
    constructor(valueType: JavaType?) : super(valueType)
    constructor(src: StdDeserializer<*>?) : super(src)

    abstract fun values(): Array<R>

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): R? {
        return p?.codec
            ?.readValue(p, String::class.java)
            ?.let { code -> values().find { it.code == code } }
    }
}

//class WideShotDeserialize : CodeEnumDeserializer<String, WideShotStatusCode>(WideShotStatusCode::class.java) {
//    override fun values(): Array<WideShotStatusCode> = WideShotStatusCode.values()
//}

//fun ObjectMapper.registerCodeEnumModule() = JsonMapper.builder().add