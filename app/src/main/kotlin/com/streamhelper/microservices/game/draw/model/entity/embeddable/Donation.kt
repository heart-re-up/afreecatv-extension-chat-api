package com.streamhelper.microservices.game.draw.model.entity.embeddable

import com.streamhelper.microservices.game.draw.model.business.security.DonationPlatform
import com.streamhelper.microservices.game.draw.model.business.security.ValueUnit
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class Donation(
    value: Long,
    valueUnit: ValueUnit,
    donationPlatform: DonationPlatform,
) {

    @Column(nullable = false)
    var value: Long = value

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var valueUnit: ValueUnit = valueUnit

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var donationPlatform: DonationPlatform = donationPlatform

}
