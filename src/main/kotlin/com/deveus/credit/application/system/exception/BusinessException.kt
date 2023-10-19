package com.deveus.credit.application.system.exception

class BusinessException(
    override val message: String?)
    : RuntimeException(message = message) {
}