package com.deveus.credit.application.system.controler

import com.deveus.credit.application.system.dto.req.CustomerDTO
import com.deveus.credit.application.system.dto.res.CustomerView
import com.deveus.credit.application.system.model.Customer
import com.deveus.credit.application.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerService: CustomerService
) {
    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDTO): ResponseEntity<CustomerView> {
        val saved: Customer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(saved))
    }

    @GetMapping
    fun findCustomer(): ResponseEntity<List<CustomerView>>{
        val listCustomer: List<CustomerView> = this.customerService.findAll().map{ it -> CustomerView(it) }
        return ResponseEntity.status(HttpStatus.OK).body(listCustomer)
    }
}