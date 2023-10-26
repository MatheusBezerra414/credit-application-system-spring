package com.deveus.credit.application.system.controller

import com.deveus.credit.application.system.dto.req.CustomerDTO
import com.deveus.credit.application.system.dto.req.CustomerUpdateDTO
import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.util.Lists
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.*
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var customerDTO: CustomerDTO

    private lateinit var jose: Customer

    @BeforeEach
    fun setup() {
        customerDTO = builderCustomerDto()
        val id = 1L
        jose = customerDTO.toEntity()
        jose.id = id
        given(customerRepository.findById(id)).willReturn(Optional.of(jose))
    }

    companion object {
        const val URL = "/api/customer"
    }

    @Test
    fun `should create customer and return 201 status`() {
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)

        `when`(customerRepository.save(any(Customer::class.java))).thenAnswer { invocation ->
            val customerEntity = invocation.getArgument(0) as Customer
            customerEntity
        }
        mockMvc.post(URL) {
            content = valueAsString
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andDo { print() }
    }

    @Test
    fun `should return list of Customers and status 200`() {
        given(customerRepository.findAll()).willReturn(Lists.list(jose))

        mockMvc.get(URL).andExpectAll {
            content { contentType(MediaType.APPLICATION_JSON) }
            status { isOk() }
            jsonPath("$[0].firstName") { value("Jose") }
            jsonPath("$[0].lastName") { value("da Silva") }
            jsonPath("$[0].cpf") { value("123456789") }
            jsonPath("$[0].income") { value("1200.0") }
            jsonPath("$[0].email") { value("jose@email.com") }
            jsonPath("$[0].password") { value("1234") }
            jsonPath("$[0].zipCode") { value("123456") }
            jsonPath("$[0].street") { value("Rua sem nome") }
        }.andDo { print() }
    }

    @Test
    fun `should return customer by id`() {
        mockMvc.get("$URL/{id}", jose.id).andExpectAll {
            content { contentType(MediaType.APPLICATION_JSON) }
            status { isOk() }
            jsonPath("$.firstName") { value("Jose") }
            jsonPath("$.lastName") { value("da Silva") }
            jsonPath("$.cpf") { value("123456789") }
            jsonPath("$.income") { value("1200.0") }
            jsonPath("$.email") { value("jose@email.com") }
            jsonPath("$.password") { value("1234") }
            jsonPath("$.zipCode") { value("123456") }
            jsonPath("$.street") { value("Rua sem nome") }
            jsonPath("$.id") { value(1) }
        }.andDo { print() }
    }

    @Test
    fun `should delete customer and status 204`() {
        mockMvc.delete("$URL/{id}", jose.id).andExpectAll {
            content { contentType(MediaType.APPLICATION_JSON) }
            status { isNoContent() }
        }.andDo { print() }
    }

    @Test
    fun `should update customer and status 204`() {
        val customerUpdateDTO = builderCustomerUpdateDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDTO)

        `when`(customerRepository.save(any(Customer::class.java))).thenAnswer { invocation ->
            val customerEntity = invocation.getArgument(0) as Customer
            customerEntity
        }
        mockMvc.patch("$URL?customerId=${jose.id}") {
            content = valueAsString
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.firstName") { value("Jose-atualizado") }
        }.andDo { print() }
    }

    fun builderCustomerDto(
        firstName: String = "Jose",
        lastName: String = "da Silva",
        cpf: String = "123456789",
        income: BigDecimal = BigDecimal.valueOf(1200.0),
        email: String = "jose@email.com",
        password: String = "1234",
        zipCode: String = "123456",
        street: String = "Rua sem nome",
    ) = CustomerDTO(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )

    fun builderCustomerUpdateDto(
        firstName: String = "Jose-atualizado",
        lastName: String = "da Silva-atualizado",
        income: BigDecimal = BigDecimal.valueOf(2100.0),
        zipCode: String = "123456-atualizado",
        street: String = "Rua-atualizado",
    ): CustomerUpdateDTO = CustomerUpdateDTO(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )
}