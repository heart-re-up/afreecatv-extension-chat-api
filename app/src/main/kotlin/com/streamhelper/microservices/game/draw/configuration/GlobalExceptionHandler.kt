package com.streamhelper.microservices.game.draw.configuration

import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler(val messageSource: MessageSource) {
//    @ExceptionHandler(UMSMessageContentBytesOverflowException::class)
//    fun foo(e: UMSMessageContentBytesOverflowException) =
//        ResponseEntity.badRequest().body(e.message)
}