package com.example.graphqlhibernatereactive.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Child(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null,
    var name: String = "",
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    var parent: Parent = Parent(),
)