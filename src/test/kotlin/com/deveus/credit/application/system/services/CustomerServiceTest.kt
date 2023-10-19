package com.deveus.credit.application.system.services

import com.deveus.credit.application.system.model.Address
import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.repository.CustomerRepository
import com.deveus.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK
    lateinit var customerRepository: CustomerRepository
    @InjectMockKs
    lateinit var customerService: CustomerService

    @Test
    fun `Should create Customer`(){
        val fakeCustomer: Customer = buildCustomer()
        every { customerRepository.save(any()) } returns fakeCustomer

        val actual: Customer = customerService.save(fakeCustomer)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.save(fakeCustomer)}
    }

    @Test
    fun `Should find customer by Id`(){
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of( fakeCustomer )

        val actual: Customer = customerService.findById(fakeId)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId)}
    }

    @Test
    fun `Should find all customer`(){
        val fakeCustomer: Customer = buildCustomer()
        val fakeCustomer2: Customer = buildCustomer(cpf = "22222222", email = "eu@email.com")
        val fakeListCustomers: List<Customer> = mutableListOf(fakeCustomer,fakeCustomer2)
        every{ customerRepository.findAll() } returns fakeListCustomers

        val actual: List<Customer> = customerService.findAll()

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeListCustomers)
        verify ( exactly = 1 ) {customerRepository.findAll()}
    }

    @Test
    fun `Should delete customer`(){
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs

        customerService.delete(fakeId)

        verify( exactly = 1) { customerRepository.findById(fakeId) }
        verify( exactly = 1) { customerRepository.delete(fakeCustomer) }
    }


    companion object{
         fun buildCustomer(
            firstName: String = "José",
            lastName: String = "Da Silva",
            cpf: String = "12345678900",
            email: String = "jose@email.com",
            password: String = "4321",
            zipCode: String = "1234567",
            street: String = "Rua sem Nome",
            income: BigDecimal = BigDecimal.valueOf(2000.00),
            id: Long = 1L,
        ): Customer = Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                zipCode = zipCode,
                street = street,
            ),
            income = income,
            id = id,
        )
    }

}