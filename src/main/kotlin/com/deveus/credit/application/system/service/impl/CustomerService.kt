package com.deveus.credit.application.system.service.impl

import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.repository.CustomerRepository
import com.deveus.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer = this.customerRepository.save(customer);

    override fun findAll(): List<Customer> = this.customerRepository.findAll()

    override fun findById(id: Long): Customer {
        return this.customerRepository.findById(id).orElseThrow{throw RuntimeException("not found!")}
    }
    override fun delete(id: Long) {
        val customer: Customer = this.findById(id)
        this.customerRepository.delete(customer)
    }
}