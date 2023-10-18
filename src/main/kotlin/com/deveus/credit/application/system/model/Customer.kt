package com.deveus.credit.application.system.model

import jakarta.annotation.Nullable
import jakarta.persistence.*
import jakarta.validation.constraints.Email
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

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE, CascadeType.PERSIST), mappedBy = "customer")
    val credit: List<Credit> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
)
