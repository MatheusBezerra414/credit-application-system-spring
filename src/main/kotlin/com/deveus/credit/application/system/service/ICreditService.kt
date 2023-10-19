package com.deveus.credit.application.system.service

import com.deveus.credit.application.system.model.Credit
import java.util.UUID

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAll(): List<Credit>
    fun findByCustomerId(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
    fun delete(id: Long)
}