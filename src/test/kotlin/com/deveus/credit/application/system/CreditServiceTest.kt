package com.deveus.credit.application.system

import com.deveus.credit.application.system.model.Credit
import com.deveus.credit.application.system.repository.CreditRepository
import com.deveus.credit.application.system.service.impl.CreditService
import com.deveus.credit.application.system.service.impl.CustomerService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

//TODO("SAVE, FINDALL, FINDBYCUSTOMER, FINDBYCREDITCODE, DELETE")

@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @BeforeEach
    fun setup(){
        MockKAnnotations.init(this)
    }
    @AfterEach
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `should create credit`(){
    }

//    companion object {
//        private fun buildCredit(
//
//        ): Credit =
//            Credit(
//
//            )
//
//    }


}