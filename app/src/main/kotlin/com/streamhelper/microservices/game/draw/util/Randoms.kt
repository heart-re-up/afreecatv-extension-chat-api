package com.streamhelper.microservices.game.draw.util

private val charsetLowerCase = ('a'..'z')
private val charsetUpperCase = ('A'..'Z')
private val charsetNumber = ('0'..'9')

fun randomStringOnlyCharacterLowerCase(length: Int) = randomString(length, lowerCase = true, upperCase = false, number = false)
fun randomStringOnlyCharacterUpperCase(length: Int) = randomString(length, lowerCase = false, upperCase = true, number = false)
fun randomStringOnlyCharacter(length: Int) = randomString(length, lowerCase = true, upperCase = true, number = false)
fun randomStringOnlyNumber(length: Int) = randomString(length, lowerCase = false, upperCase = false, number = true)
fun randomString(length: Int, lowerCase: Boolean = true, upperCase: Boolean = true, number: Boolean = true): String {
    val charset = mutableListOf<Char>()
    if (lowerCase) charset += charsetLowerCase
    if (upperCase) charset += charsetUpperCase
    if (number) charset += charsetNumber
    return (1..length)
        .map { charset.random() }
        .joinToString()
}