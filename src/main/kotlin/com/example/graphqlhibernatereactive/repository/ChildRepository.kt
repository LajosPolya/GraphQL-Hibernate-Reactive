package com.example.graphqlhibernatereactive.repository

import com.example.graphqlhibernatereactive.entity.Child
import com.example.graphqlhibernatereactive.entity.Parent
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ChildRepository @Autowired constructor(
    private val sessionFactory: SessionFactory
) : BaseRepository() {

    fun createChild(parent: Parent): Mono<Child> {

        return sessionFactory.withTransaction { session ->
            val child1 = Child(null, "Child1", parent)
            session.persist(child1).chain(session::flush).replaceWith(child1)
        }.toMono()
    }

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