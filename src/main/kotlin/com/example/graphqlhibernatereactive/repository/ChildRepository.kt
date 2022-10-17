package com.example.graphqlhibernatereactive.repository

import com.example.graphqlhibernatereactive.entity.Child
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ChildRepository @Autowired constructor(
    private val sessionFactory: SessionFactory
) : BaseRepository() {

    fun findForParent(id: Int): Mono<List<Child>> {
        return sessionFactory.withSession { session ->
            val criteriaBuilder = sessionFactory.criteriaBuilder
            val criteriaQuery = criteriaBuilder.createQuery(Child::class.java)
            val root = criteriaQuery.from(Child::class.java)

            val predicates = mutableListOf(
                criteriaBuilder.equal(root.get<Int>("parent"), id)
            )

            criteriaQuery.where(*predicates.toTypedArray())

            session.createQuery(criteriaQuery).resultList
        }.toMono()
    }

    fun findForParentTransaction(id: Int): Mono<List<Child>> {
        return sessionFactory.withTransaction { session ->
            val criteriaBuilder = sessionFactory.criteriaBuilder
            val criteriaQuery = criteriaBuilder.createQuery(Child::class.java)
            val root = criteriaQuery.from(Child::class.java)

            val predicates = mutableListOf(
                criteriaBuilder.equal(root.get<Int>("parent"), id)
            )

            criteriaQuery.where(*predicates.toTypedArray())

            session.createQuery(criteriaQuery).resultList
        }.toMono()
    }
}