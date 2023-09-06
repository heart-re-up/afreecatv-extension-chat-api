package com.streamhelper.microservices.game.draw.model

import com.streamhelper.microservices.game.draw.model.so.Validatable

/**
 * 클라이언트 -> 서버
 *
 * 클라이언트와 정보를 교환하기 위한 모델입니다.
 * 현재 프로젝트에서는 클라이언트에서 서버로 전송하는 데이터에만 사용합니다.
 * 서버에서 클라이언트로 전송하는 데이터는 [RenderedObject] 를 참조하세요.
 */
interface DataTransferObject<SO : ServiceObject<*>> : Validatable {
    fun toServiceObject(): SO
}

/**
 * 서비스 레이어 전용
 *
 * 서비스 레이어에서 수신하는 모델입니다.
 * 클라이언트와 통신하는데 사용되지 않습니다. 따라서 민감한 정보를 담아서 처리할 때 좋은 계층의 모델입니다.
 * 엔터티로의 단방향 전환만 지원합니다.
 */
interface ServiceObject<ENTITY> : Validatable {
    fun toEntity(): ENTITY
}

/**
 * 서버 -> 클라이언트
 *
 * 클라이언트와 정보를 교환하기 위한 모델입니다.
 * 현재 프로젝트에서는 서버에서 클라이언트로 전송하는 데이터에만 사용합니다.
 * 서버에서 클라이언트로 전송하는 데이터는 [DataTransferObject] 를 참조하세요.
 */
interface RenderedObject {

}