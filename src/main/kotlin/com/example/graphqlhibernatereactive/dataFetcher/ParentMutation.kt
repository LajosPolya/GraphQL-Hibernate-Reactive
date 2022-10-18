package com.example.graphqlhibernatereactive.dataFetcher

import com.example.graphqlhibernatereactive.repository.ChildRepository
import com.example.graphqlhibernatereactive.repository.ParentRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

@DgsComponent
class ParentMutation @Autowired constructor(
    private val parentRepository: ParentRepository,
    private val childRepository: ChildRepository,
) {

    @DgsMutation
    fun createData(): Mono<Boolean> {
        return parentRepository.createParent().flatMap {
            childRepository.createChild(it)
        }.flatMap {
            parentRepository.createParent()
        }.flatMap {
            childRepository.createChild(it)
        }.map { true }
    }
}