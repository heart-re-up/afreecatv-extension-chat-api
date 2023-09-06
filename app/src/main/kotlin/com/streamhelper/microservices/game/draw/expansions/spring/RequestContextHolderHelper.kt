package com.streamhelper.microservices.game.draw.expansions.spring

import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

val servletRequestAttributes
    get() = RequestContextHolder
        .currentRequestAttributes()
        .let { it as ServletRequestAttributes }
val servletRequest get() = servletRequestAttributes.request
val servletResponse get() = servletRequestAttributes.response
val servletRequestHeaders: MultiValueMap<String, String> = servletRequest.let {
    val map = HttpHeaders()
    it.headerNames.asSequence()
        .forEach { headerName -> map.addAll(headerName, it.getHeaders(headerName).toList()) }
    map
}
val servletResponseHeaders: MultiValueMap<String, String> = servletResponse.let {
    val map = HttpHeaders()
    it?.headerNames
        ?.forEach { headerName -> map.addAll(headerName, it.getHeaders(headerName).toList()) }
    map
}
