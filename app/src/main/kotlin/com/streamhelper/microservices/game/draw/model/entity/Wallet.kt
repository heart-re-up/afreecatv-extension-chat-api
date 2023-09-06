package com.streamhelper.microservices.game.draw.model.entity

import com.streamhelper.microservices.game.draw.model.entity.mapped.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

/**
 * 메시지 서버를 사용하는 유저. 즉 다른 서버입니다.
 */
@Entity
class Wallet(
    point: Long,
) : PrimaryKeyEntity() {

    /**
     * 포인트
     */
    @Column
    var point: Long = point
        private set

    fun addPoint(point: Long) {
        this.point += point
    }

    fun reducePoint(point: Long) {
        this.point -= point
    }

}