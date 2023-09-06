package com.streamhelper.microservices.game.draw.exception

class MyUnauthorizedException() :
    MyAuthException("허가되지 않은 사용자입니다.")