package com.streamhelper.microservices.game.draw.expansions.retrofit

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private infix fun String.with(value: String) =
    MultipartBody.Part.createFormData(this, value)

internal class BooleanPartConverter : MultipartHelper.PartConverter {
    override fun toMultipartPart(name: String, value: Any): MultipartBody.Part? =
        (value as? Boolean)?.toString()
            ?.let { name with it }
}

internal class CharPartConverter : MultipartHelper.PartConverter {
    override fun toMultipartPart(name: String, value: Any): MultipartBody.Part? =
        (value as? Char)?.toString()
            ?.let { name with it }
}

internal class NumberPartConverter : MultipartHelper.PartConverter {
    override fun toMultipartPart(name: String, value: Any): MultipartBody.Part? =
        (value as? Number)?.toString()
            ?.let { name with it }
}

internal class StringPartConverter : MultipartHelper.PartConverter {
    override fun toMultipartPart(name: String, value: Any): MultipartBody.Part? =
        (value as? String)
            ?.let { name with it }
}

internal class FilePartConverter : MultipartHelper.PartConverter {
    override fun toMultipartPart(name: String, value: Any) =
        (value as? File)
            ?.let {
                MultipartBody.Part.createFormData(
                    name,
                    name,
                    it.asRequestBody("text/plain".toMediaTypeOrNull())
                )
            }

}