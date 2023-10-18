package com.deveus.credit.application.system.repository

import com.deveus.credit.application.system.model.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CreditRepository: JpaRepository<Credit, Long> {

    @Query( value= "SELECT * FROM CREDITO WHERE CUSTOMER_ID=?1", nativeQuery = true )
    fun findByCustomerId(customerId : Long): Credit

    fun findByCreditCode(creditCode: UUID): Credit?
}

