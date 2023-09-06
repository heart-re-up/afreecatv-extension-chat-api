package com.streamhelper.microservices.game.draw

import org.junit.jupiter.api.Test

class TokenTest {
    @Test
    fun testTokenReplace() {
        val template = """
                #{임대업체}에서 #{문서이름} 발행되었습니다.

                #{고객명}님 안녕하세요.
                #{임대업체}에서 #{거래처}님께 #{문서이름} 발행했습니다.
                
                다음 링크를 통해서 문서 확인 페이지로 이동합니다.
                
                - 링크: #{url}
                
                #{문서페이지이동버튼}
                """.trimIndent()
        val tokens = mapOf(
            "#{임대업체}" to "틴텍씨엔씨",
            "#{문서이름}" to "거래명세서",
            "#{고객명}" to "정재위",
            "#{거래처}" to "사무실1",
            "#{url}" to "http://www.google.com",
        )
        val result = tokens.entries.fold(template){
                string, token -> string.replace(token.key, token.value)
        }
        println(result)
    }

}