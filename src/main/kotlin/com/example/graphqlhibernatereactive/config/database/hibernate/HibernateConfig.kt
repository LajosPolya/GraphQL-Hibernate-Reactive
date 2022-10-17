package com.example.graphqlhibernatereactive.config.database.hibernate

import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.hibernate.reactive.provider.ReactivePersistenceProvider
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.domain.EntityScanner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.example.graphqlhibernatereactive.config.database.JdbcProperties
import javax.persistence.Entity
import javax.persistence.EntityManagerFactory
import javax.persistence.spi.PersistenceUnitInfo

@Configuration
@EntityScan("com.example.graphqlhibernatereactive.entity")
@EnableConfigurationProperties(JdbcProperties::class)
class HibernateConfig {

    @Bean
    fun sessionFactory(entityManagerFactory: EntityManagerFactory): SessionFactory =
        entityManagerFactory.unwrap(SessionFactory::class.java)

    @Bean
    fun entityManagerFactory(
        jdbcProperties: JdbcProperties,
        persistenceUnitInfo: PersistenceUnitInfo
    ): EntityManagerFactory {
        val provider = ReactivePersistenceProvider()
        return provider.createContainerEntityManagerFactory(persistenceUnitInfo, jdbcProperties.toProperties())
    }

    @Bean
    fun persistenceUnitInfo(jdbcProperties: JdbcProperties, entitySet: Set<Class<*>>): PersistenceUnitInfo {
        return HibernatePersistenceUnitInfo(jdbcProperties.toProperties(), entitySet.map { it.name }.toMutableList())
    }

    @Bean
    fun entitySet(applicationContext: ApplicationContext) = EntityScanner(applicationContext).scan(Entity::class.java)
}