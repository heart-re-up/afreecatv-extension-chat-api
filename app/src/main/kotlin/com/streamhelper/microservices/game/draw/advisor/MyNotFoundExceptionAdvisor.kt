package com.streamhelper.microservices.game.draw.advisor

import com.streamhelper.microservices.game.draw.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MyNotFoundExceptionAdvisor {

    /**
     * 계정을 찾을 수 없는 예외 처리.
     * 401 처리한다.
     */
    @ExceptionHandler(MyAccountNotFoundException::class)
    fun handleAccountNotFoundException(e: MyAccountNotFoundException) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)

    /**
     * 리소스를 찾을 수 없는 예외 처리.
     * 404 처리한다.
     */
    @ExceptionHandler(MyResourceNotFoundException::class)
    fun handleResourceNotFoundException(e: MyResourceNotFoundException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

}