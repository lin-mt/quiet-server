/*
 * Copyright (C) 2023 lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.github.quiet"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

var queryDslVersion = "5.0.0"
var jakartaPersistenceVersion = "3.1.0"
var byteBuddyVersion = "1.12.23"
var commonsLang3Version = "3.12.0"
var commonsCollections4Version = "4.4"
var googleGuavaVersion = "31.1-jre"
var jacksonModuleJakartaXmlbindAnnotationsVersion = "2.14.2"
var mapstructVersion = "1.5.3.Final"
var okhttpVersion = "4.10.0"
var blazePersistenceVersion = "1.6.8"
var swaggerParserVersion = "2.1.11"
var minioVersion = "8.5.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.minio:minio:${minioVersion}")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    implementation("com.google.guava:guava:${googleGuavaVersion}")
    implementation("net.bytebuddy:byte-buddy:${byteBuddyVersion}")
    implementation("com.squareup.okhttp3:okhttp:${okhttpVersion}")
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    implementation("com.querydsl:querydsl-core:${queryDslVersion}")
    implementation("net.bytebuddy:byte-buddy-agent:${byteBuddyVersion}")
    implementation("org.apache.commons:commons-lang3:${commonsLang3Version}")
    implementation("io.swagger.parser.v3:swagger-parser:${swaggerParserVersion}")
    implementation("org.apache.commons:commons-collections4:${commonsCollections4Version}")
    implementation("com.fasterxml.jackson.module:jackson-module-jakarta-xmlbind-annotations:${jacksonModuleJakartaXmlbindAnnotationsVersion}")
    compileOnly("org.projectlombok:lombok")
    compileOnly("com.blazebit:blaze-persistence-core-api-jakarta:${blazePersistenceVersion}")
    compileOnly("com.blazebit:blaze-persistence-integration-querydsl-expressions-jakarta:${blazePersistenceVersion}")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.blazebit:blaze-persistence-core-impl-jakarta:${blazePersistenceVersion}")
    runtimeOnly("com.blazebit:blaze-persistence-integration-hibernate-6.0:${blazePersistenceVersion}")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:${jakartaPersistenceVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
    compilerArgs.add("-Amapstruct.unmappedTargetPolicy=IGNORE")
    compilerArgs.add("-Xlint:unchecked")
}
