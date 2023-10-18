package com.deveus.credit.application.system.repository

import com.deveus.credit.application.system.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {


}