package com.example.graphqlhibernatereactive.dataFetcher

import com.example.graphqlhibernatereactive.repository.ChildRepository
import com.example.graphqlhibernatereactive.schema.DgsConstants
import com.example.graphqlhibernatereactive.schema.types.Child
import com.example.graphqlhibernatereactive.schema.types.Parent
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono


@DgsComponent
class ChildQuery @Autowired constructor(
    private val childRepository: ChildRepository,
) {

    @DgsData(parentType = DgsConstants.PARENT.TYPE_NAME)
    fun children(dfe: DgsDataFetchingEnvironment): Mono<List<Child>> {
        val member = dfe.getSource<Parent>()
        return childRepository.findForParent(member.id!!).map {
            it.map { child -> Child(child.id, child.name) }
        }
    }
}