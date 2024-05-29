package com.example.kotlin_2405.entity

import jakarta.persistence.*

@MappedSuperclass
abstract class BaseNameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(length = 20, nullable = false)
    var name: String = ""
}