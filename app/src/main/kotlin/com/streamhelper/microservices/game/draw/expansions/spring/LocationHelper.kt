package com.streamhelper.microservices.game.draw.expansions.spring

import org.springframework.web.util.UriComponentsBuilder

//fun resourceLocation(uuid: UUID?) = resourceLocation(uuid.toString())
fun resourceLocation(value: Any) =
    UriComponentsBuilder.fromUriString(servletRequest.requestURI)
        .apply { path("/$value") }
        .build()
        .encode()
        .toUri()