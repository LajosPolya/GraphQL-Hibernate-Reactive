package com.example.graphqlhibernatereactive.dataFetcher

import com.example.graphqlhibernatereactive.repository.ParentRepository
import com.example.graphqlhibernatereactive.schema.types.Parent
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@DgsComponent
class ParentQuery @Autowired constructor(
    private val parentRepository: ParentRepository,
) {

    @DgsQuery
    fun parents(): Mono<List<Parent>> {
        return parentRepository.withSession().map {
            it.map { parent -> Parent(parent.id, parent.name) }
        }.switchIfEmpty {
            throw DgsEntityNotFoundException("ActiveState not found.")
        }
    }
}