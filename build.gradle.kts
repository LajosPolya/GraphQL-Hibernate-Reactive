import com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("com.netflix.dgs.codegen") version "5.1.17"
}

group = "com.example.graphqlhibernatereactive"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.2"))
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:5.2.4"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-webflux-starter")
	implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.7.10"))
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.7")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test:3.4.23")
	implementation("org.hibernate.reactive:hibernate-reactive-core:1.1.7.Final")
	implementation("io.vertx:vertx-mysql-client:4.3.3")
	implementation("io.smallrye.reactive:mutiny-reactor:1.7.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<GenerateJavaTask> {
	schemaPaths = mutableListOf("$projectDir/src/main/resources/schema")
	packageName = "com.example.graphqlhibernatereactive.schema"
	generateClient = false
	typeMapping = mutableMapOf(
		Pair("JSON", "kotlin.collections.Map<String, Any>")
	)
}