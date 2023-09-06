package com.streamhelper.microservices.game.draw.expansions.retrofit

fun Any.toPartMap() = MultipartHelper.toSequenceParts(this).toMap()
fun Any.toPartList() = MultipartHelper.toSequenceParts(this).toList().map { it.second }
//fun Any.toFieldMap() =