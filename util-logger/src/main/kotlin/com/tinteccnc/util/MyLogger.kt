package com.tinteccnc.util

import io.micrometer.common.util.internal.logging.InternalLogger
import io.micrometer.common.util.internal.logging.Slf4JLoggerFactory

interface MyLogger {
    fun logger(): InternalLogger = Slf4JLoggerFactory.getInstance(this::class.qualifiedName)
    fun info(message: String) = logger().info(message)
    fun debug(message: String) = logger().debug(message)
    fun warn(message: String) = logger().warn(message)
    fun error(message: String) = logger().error(message)
}