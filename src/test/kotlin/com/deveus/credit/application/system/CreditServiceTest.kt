package com.deveus.credit.application.system

import com.deveus.credit.application.system.model.Credit
import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.repository.CreditRepository
import com.deveus.credit.application.system.service.impl.CreditService
import com.deveus.credit.application.system.service.impl.CustomerService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should create credit`() {
        val fakeCredit = buildCredit()
        val fakeCustomerId = 1L
        every { customerService.findById(fakeCustomerId) } returns fakeCredit.customer!!
        every { creditRepository.save(fakeCredit) } returns fakeCredit

        val actual: Credit = creditService.save(fakeCredit)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)

        verify(exactly = 1) { customerService.findById(fakeCustomerId) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should return all credits by customer id`() {
        val fakeCustomerId = 1L
        val fakeListCredit = mutableListOf<Credit>(buildCredit(), buildCredit())
        every { creditRepository.findByCustomerId(fakeCustomerId) } returns fakeListCredit

        val actual: List<Credit> = creditService.findByCustomerId(fakeCustomerId)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isSameAs(fakeListCredit)

        verify(exactly = 1) { creditRepository.findByCustomerId(fakeCustomerId) }

    }

    @Test
    fun `should return all credits`() {
        val fakeListCredit = mutableListOf<Credit>(buildCredit(), buildCredit())
        every { creditRepository.findAll() } returns fakeListCredit

        val actual: List<Credit> = creditService.findAll()

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isSameAs(fakeListCredit)

        verify(exactly = 1) { creditRepository.findAll() }

    }

    @Test
    fun `should return credit by credit code`() {
        val fakeCustomerId = 1L
        val fakeCreditCode = UUID.randomUUID()
        val fakeCredit = buildCredit(customer = CustomerServiceTest.buildCustomer(id = fakeCustomerId))
        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        val actual = creditService.findByCreditCode(fakeCustomerId, fakeCreditCode)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)

        verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    @Test
    fun `should delete credit by id`() {
        val fakeCreditId = 1L
        val fakeCredit = buildCredit()
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        every { creditRepository.deleteById(fakeCreditId) } just runs

        creditService.delete(fakeCreditId)

        verify(exactly = 1) { creditRepository.deleteById(fakeCreditId) }

    }

    companion object {
        private fun buildCredit(
            creditCode: UUID = UUID.randomUUID(),
            creditValue: BigDecimal = BigDecimal.valueOf(100.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2),
            numberOfInstallments: Int = 10,
            customer: Customer = CustomerServiceTest.buildCustomer()

        ): Credit =
            Credit(
                creditCode = creditCode,
                creditValue = creditValue,
                dayFirstInstallment = dayFirstInstallment,
                numberOfInstallments = numberOfInstallments,
                customer = customer
            )

    }


}