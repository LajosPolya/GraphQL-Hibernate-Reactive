package com.example.graphqlhibernatereactive.spec

import com.netflix.graphql.dgs.client.MonoGraphQLClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ParentChildSpec(@LocalServerPort port: Int) {
    private lateinit var monoGraphQLClient: MonoGraphQLClient
    val webClient = WebClient.create("http://localhost:$port/graphql")

    /**
     * This test populates the database then
     * fetches the Parents' Children using withSession.
     */
    @Test
    fun parentsChildrenWithSession() {
        monoGraphQLClient = MonoGraphQLClient.createWithWebClient(webClient) {
                headers -> headers.add("withSession", "true")
        }

        // populate DB with data
        val mutation = "mutation { createData }"
        var response = monoGraphQLClient.reactiveExecuteQuery(mutation).block()
        assertTrue(response!!.extractValue<Boolean>("createData"))

        val query = "{ parents { name children { name } }}"
        response = monoGraphQLClient.reactiveExecuteQuery(query).block()
        val parents: List<*>? = response?.extractValueAsObject(
            "parents[*]",
            List::class.java
        )

        parents?.forEach {
            val children = (it as LinkedHashMap<*, *>)["children"]
            assertNotNull(children)
            assertNotEquals((children as List<*>).size, 0)
        }
    }

    /**
     * This test populates the database then
     * fetches the Parents' Children using withTransaction
     */
    @Test
    fun parentsChildrenWithTransaction() {
        monoGraphQLClient = MonoGraphQLClient.createWithWebClient(webClient)

        // populate DB with data
        val mutation = "mutation { createData }"
        var response = monoGraphQLClient.reactiveExecuteQuery(mutation).block()
        assertTrue(response!!.extractValue<Boolean>("createData"))

        val query = "{ parents { name children { name } }}"
        response = monoGraphQLClient.reactiveExecuteQuery(query).block()
        val parents: List<*>? = response?.extractValueAsObject(
            "parents[*]",
            MutableList::class.java
        )

        parents?.forEach {
            val children = (it as LinkedHashMap<*, *>)["children"]
            assertNotNull(children)
            assertNotEquals((children as List<*>).size, 0)
        }
    }
}
