package com.deveus.credit.application.system.dto.req

import com.deveus.credit.application.system.model.Address
import com.deveus.credit.application.system.model.Customer
import java.math.BigDecimal

data class CustomerUpdateDTO(
    val firstName: String,
    val lastName: String,
    val income: BigDecimal,
    val zipCode: String,
    val street: String,
) {
    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.address.zipCode = this.zipCode
        customer.address.street = this.street
        customer.income = income
        return customer
    }
}


