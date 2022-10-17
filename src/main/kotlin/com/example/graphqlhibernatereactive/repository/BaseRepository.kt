package com.example.graphqlhibernatereactive.repository

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.converters.uni.UniReactorConverters
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import reactor.core.publisher.Mono

abstract class BaseRepository {
    fun <T> Uni<T>.toMono(): Mono<T> = this.convert().with(UniReactorConverters.toMono())
}