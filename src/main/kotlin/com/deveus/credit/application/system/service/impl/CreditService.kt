package com.deveus.credit.application.system.service.impl

import com.deveus.credit.application.system.model.Credit
import com.deveus.credit.application.system.repository.CreditRepository
import com.deveus.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
) : ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
     return this.creditRepository.save(credit)
    }

    override fun findAll(): List<Credit> = this.creditRepository.findAll()

    override fun findByCustomer(customerId: Long): Credit =
        this.findByCustomer(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit =
            (this.creditRepository.findByCreditCode(creditCode) ?: throw RuntimeException("Not found $creditCode"))
        return if (credit.customer?.id == customerId) credit
        else throw RuntimeException("Please contact the Manager account")
    }

    override fun delete(id: Long) {
        this.creditRepository.deleteById(id)
    }
}