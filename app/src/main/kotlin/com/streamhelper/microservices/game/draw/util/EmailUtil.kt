package com.streamhelper.microservices.game.draw.util

object EmailUtil {
    const val MAX_LENGTH_EMAIL_LOCAL_PART = 64
    const val MAX_LENGTH_EMAIL_DOMAIN_PART = 255
    const val MAX_LENGTH_EMAIL = MAX_LENGTH_EMAIL_LOCAL_PART + MAX_LENGTH_EMAIL_DOMAIN_PART + 1 // @ 문자를 1개 더한다.

    fun String.validateEmail(): Pair<String, String> {
        // 전체 길이 및 정규식 유효성 검증
        when {
            this.length > MAX_LENGTH_EMAIL -> throw IllegalArgumentException("Email is too long.")
            REGEX_EMAIL.toRegex().matches(this).not() -> throw IllegalArgumentException("Invalid email.")
            else -> Unit
        }
        // 로컬 및 도메인 파트 길이 검사
        val (local, domain) = this.split("@")
        when {
            local.length > MAX_LENGTH_EMAIL_LOCAL_PART -> throw IllegalArgumentException("Email local part is too long.")
            domain.length > MAX_LENGTH_EMAIL_DOMAIN_PART -> throw IllegalArgumentException("Email domain part is too long.")
        }
        return Pair(local, domain)
    }
}