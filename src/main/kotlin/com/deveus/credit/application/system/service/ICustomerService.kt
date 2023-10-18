package com.deveus.credit.application.system.service

import com.deveus.credit.application.system.model.Customer

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findAll(): List<Customer>
    fun findById(id: Long): Customer
    fun delete(id: Long): Unit
}