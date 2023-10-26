package com.deveus.credit.application.system.model

import com.deveus.credit.application.system.dto.req.CustomerDTO
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import java.math.BigDecimal

@Entity
@Table(name = "cliente")
data class Customer(
    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false, unique = true)
    val cpf: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Embedded
    @Column(nullable = false)
    val address: Address = Address(),

    @Column(nullable = false)
    val income: BigDecimal,

    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.PERSIST), mappedBy = "customer")
    val credit: List<Credit> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
