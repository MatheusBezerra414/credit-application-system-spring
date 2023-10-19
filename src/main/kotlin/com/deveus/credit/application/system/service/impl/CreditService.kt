package com.deveus.credit.application.system.service.impl

import com.deveus.credit.application.system.exception.BusinessException
import com.deveus.credit.application.system.model.Credit
import com.deveus.credit.application.system.repository.CreditRepository
import com.deveus.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreditService(
    private val customerService: CustomerService,
    private val creditRepository: CreditRepository
) : ICreditService {

    override fun save(credit: Credit): Credit {
        this.validDayFirstInstallment(credit.dayFirstInstallment)
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAll(): List<Credit> = this.creditRepository.findAll()

    override fun findByCustomerId(customerId: Long): List<Credit> = this.creditRepository.findByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit =
            (this.creditRepository.findByCreditCode(creditCode) ?: throw BusinessException("Not found $creditCode"))
        return if (credit.customer?.id == customerId) credit
        else throw BusinessException("Please contact the Manager account")
    }

    override fun delete(id: Long) {
        this.creditRepository.deleteById(id)
    }

    private fun validDayFirstInstallment(dayFirstInstallment: LocalDate): Boolean {
        return if (dayFirstInstallment.isBefore(LocalDate.now().plusMonths(3))) true
        else throw BusinessException("Invalid Date")
    }
}