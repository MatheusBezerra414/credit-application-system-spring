package com.deveus.credit.application.system.model

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    var zipCode: String = "",
    var street: String = ""
)
