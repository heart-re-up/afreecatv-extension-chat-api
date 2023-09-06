package com.streamhelper.microservices.game.draw.model.entity.mapped

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import java.util.*
import kotlin.jvm.Transient

/**
 * 모든 엔티티가 가져야 하는 기본적인 속성을 제어하는 추상 클래스입니다.
 *
 * 기본키 [id], 생성시간 [createdAt], 갱신시간 [updatedAt] 등을 자동으로 제어합니다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class PrimaryKeyEntity(
    id: UUID = UlidCreator.getMonotonicUlid().toUuid(),
    now: ZonedDateTime = ZonedDateTime.now()
) : Persistable<UUID> {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private val id: UUID = id

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: ZonedDateTime = now

    @LastModifiedDate
    @Column(nullable = false, updatable = true)
    var updatedAt: ZonedDateTime = now
        protected set

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    @JsonIgnore
    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is HibernateProxy && this::class != other::class) return false
        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    private fun getIdentifier(obj: Any): Any? {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as PrimaryKeyEntity).id
        }
    }

//    @PrePersist
//    protected fun prePersist() {
//        createdAt = ZonedDateTime.now()
//    }

//    @PreUpdate
//    protected fun preUpdate() {
//        updatedAt = ZonedDateTime.now()
//    }

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }

}