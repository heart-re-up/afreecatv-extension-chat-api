package com.streamhelper.microservices.game.draw.exception

class MyAccountNotFoundException(value: Any?) :
    MyNotFoundException("제공된 정보로 사용자 계정을 찾을 수 없습니다. value=${value.toString()}")