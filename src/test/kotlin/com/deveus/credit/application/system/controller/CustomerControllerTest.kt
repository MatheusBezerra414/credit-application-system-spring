package com.deveus.credit.application.system.controller

import com.deveus.credit.application.system.dto.req.CustomerDTO
import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest{

    @Autowired
    lateinit var mockMvc:MockMvc

    @MockBean
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup(wac: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    companion object{
        const val URL = "/api/customer"
    }

    @Test
    fun `should create customer and return 201 status`() {
        val customer = builderCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customer)

        `when`(customerRepository.save(any(Customer::class.java))).thenAnswer { invocation ->
            val customerEntity = invocation.getArgument(0) as Customer
            customerEntity
        }
        mockMvc.post(URL){
            content = valueAsString
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andDo { print() }
    }

    @Test
    fun `should return status 200`(){
        mockMvc.get(URL).andExpectAll {
            content { contentType(MediaType.APPLICATION_JSON) }
            status { isOk() }
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
}