package com.streamhelper.microservices.game.draw.model.entity.embeddable

import com.streamhelper.microservices.game.draw.util.EmailUtil.MAX_LENGTH_EMAIL
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Phone(
    number: String,
) {

    @Column(nullable = false, length = MAX_LENGTH_EMAIL, unique = true)
    var number: String = number

}
