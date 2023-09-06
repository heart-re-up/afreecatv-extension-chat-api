package com.streamhelper.microservices.game.draw.model.entity.embeddable

import com.streamhelper.microservices.game.draw.util.EmailUtil.MAX_LENGTH_EMAIL
import com.streamhelper.microservices.game.draw.util.EmailUtil.MAX_LENGTH_EMAIL_DOMAIN_PART
import com.streamhelper.microservices.game.draw.util.EmailUtil.MAX_LENGTH_EMAIL_LOCAL_PART
import com.streamhelper.microservices.game.draw.util.EmailUtil.validateEmail
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Email {

    @Column(name = "email", nullable = false, length = MAX_LENGTH_EMAIL, unique = true)
    var value: String

    @Column(nullable = false, length = MAX_LENGTH_EMAIL_LOCAL_PART)
    var emailLocalPart: String

    @Column(nullable = false, length = MAX_LENGTH_EMAIL_DOMAIN_PART)
    var emailDomainPart: String

    constructor(email: String) {
        // 유효성 검사
        val (local, domain) = email.validateEmail()
        // 데이터 설정
        this.value = email
        this.emailLocalPart = local
        this.emailDomainPart = domain
    }
}
