package com.streamhelper.microservices.game.draw.advisor

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import com.tinteccnc.util.MyLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.sql.SQLIntegrityConstraintViolationException

@ControllerAdvice
class GlobalExceptionAdvisor : MyLogger {

    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
    fun handleDuplicateEntry(e: SQLIntegrityConstraintViolationException) =
        ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)

    @ExceptionHandler(InvalidDefinitionException::class)
    fun handleJacksonInvalidDefinitionException(e: InvalidDefinitionException) =
        ResponseEntity.status(HttpStatus.CONFLICT).body(e.message).also { error(e) }

}