package com.example.graphqlhibernatereactive.config.database

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import com.example.graphqlhibernatereactive.config.database.JdbcProperties.Companion.JAVAX_PREFIX
import java.util.Properties

@ConstructorBinding
@ConfigurationProperties(prefix = JAVAX_PREFIX)
@Suppress("ConfigurationProperties")
data class JdbcProperties(val url: String, val user: String, val password: String) {
    companion object {
        const val JAVAX_PREFIX = "javax.persistence.jdbc"
    }

    fun toProperties(): Properties {
        val props = Properties()
        props.setProperty("$JAVAX_PREFIX.url", url)
        props.setProperty("$JAVAX_PREFIX.user", user)
        props.setProperty("$JAVAX_PREFIX.password", password)
        props.setProperty(
            "hibernate.physical_naming_strategy",
            "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"
        )
        props.setProperty("hibernate.show_sql", "true")
        // props.setProperty("hibernate.format_sql", "true")
        props.setProperty("hibernate.highlight_sql", "true")
        return props
    }
}