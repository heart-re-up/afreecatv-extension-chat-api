package com.streamhelper.microservices.game.draw.exception

/**
 * 리소스에 접근할 때 찾지 못한 경우 예외입니다.
 */
class MyResourceNotFoundException(message: String) : MyNotFoundException(message)