package com.example.graphqlhibernatereactive.config.database.hibernate

import org.hibernate.reactive.provider.ReactivePersistenceProvider
import java.net.URL
import java.util.Properties
import javax.persistence.SharedCacheMode
import javax.persistence.ValidationMode
import javax.persistence.spi.ClassTransformer
import javax.persistence.spi.PersistenceUnitInfo
import javax.persistence.spi.PersistenceUnitTransactionType
import javax.sql.DataSource

class HibernatePersistenceUnitInfo(private val props: Properties, private val classNames: MutableList<String>) :
    PersistenceUnitInfo {
    companion object {
        const val PERSISTENCE_UNIT_NAME = "graphqlHibernateReactive"
    }

    override fun getPersistenceUnitName(): String = PERSISTENCE_UNIT_NAME

    override fun getPersistenceProviderClassName(): String = ReactivePersistenceProvider::class.java.name

    override fun getTransactionType(): PersistenceUnitTransactionType? = null

    override fun getJtaDataSource(): DataSource? = null

    override fun getNonJtaDataSource(): DataSource? = null

    override fun getMappingFileNames(): MutableList<String>? = null

    override fun getJarFileUrls(): MutableList<URL>? = null

    override fun getPersistenceUnitRootUrl(): URL? = null

    override fun getManagedClassNames(): MutableList<String> = classNames

    override fun excludeUnlistedClasses(): Boolean = false

    override fun getSharedCacheMode(): SharedCacheMode? = null

    override fun getValidationMode(): ValidationMode? = null

    override fun getProperties(): Properties = props

    override fun getPersistenceXMLSchemaVersion(): String? = null

    override fun getClassLoader(): ClassLoader? = null

    override fun addTransformer(transformer: ClassTransformer?) {
        // noop
    }

    override fun getNewTempClassLoader(): ClassLoader? = null
}