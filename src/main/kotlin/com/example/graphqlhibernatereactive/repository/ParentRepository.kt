package com.example.graphqlhibernatereactive.repository

import com.example.graphqlhibernatereactive.entity.Parent
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ParentRepository @Autowired constructor(
    private val sessionFactory: SessionFactory
) : BaseRepository() {

    fun withSession(): Mono<List<Parent>> {
        return sessionFactory.withSession { session ->
            val criteriaBuilder = sessionFactory.criteriaBuilder
            val criteriaQuery = criteriaBuilder.createQuery(Parent::class.java)
            criteriaQuery.from(Parent::class.java)

            session.createQuery(criteriaQuery).resultList
        }.toMono()
    }

    fun withTransaction(): Mono<List<Parent>> {
        return sessionFactory.withTransaction { session ->
            val criteriaBuilder = sessionFactory.criteriaBuilder
            val criteriaQuery = criteriaBuilder.createQuery(Parent::class.java)
            criteriaQuery.from(Parent::class.java)

            session.createQuery(criteriaQuery).resultList
        }.toMono()
    }
}