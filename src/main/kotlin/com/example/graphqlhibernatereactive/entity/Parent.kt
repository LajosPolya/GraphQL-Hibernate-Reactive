package com.example.graphqlhibernatereactive.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Parent(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null,
    var name: String = "",
    @OneToMany(mappedBy = "parent") val advertisers: List<Child> = mutableListOf()
)