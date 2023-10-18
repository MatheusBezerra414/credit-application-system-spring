package com.deveus.credit.application.system.model

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    val zipCode: String = "",
    val street: String = ""
)
