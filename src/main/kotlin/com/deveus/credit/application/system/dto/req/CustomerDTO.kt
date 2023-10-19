package com.deveus.credit.application.system.dto.req

import com.deveus.credit.application.system.model.Address
import com.deveus.credit.application.system.model.Customer
import java.math.BigDecimal

data class CustomerDTO(
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val password: String,
    val zipCode: String,
    val street: String,
) {
    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        email = this.email,
        password = this.password,
        address = Address(zipCode = this.zipCode, street = this.street),
        income = income,
    )
}


