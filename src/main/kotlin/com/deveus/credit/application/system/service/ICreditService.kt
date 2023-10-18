package com.deveus.credit.application.system.service

import com.deveus.credit.application.system.model.Credit
import com.deveus.credit.application.system.model.Customer
import java.util.UUID

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAll(): List<Credit>
    fun findByCustomer(customerId: Long): Credit
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
    fun delete(id: Long): Unit
}