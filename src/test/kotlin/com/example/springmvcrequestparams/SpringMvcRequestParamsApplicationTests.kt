package com.example.springmvcrequestparams

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
class SpringMvcRequestParamsApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `should get with request params`() {
        // when
        val response = restTemplate.getForEntity<Pageable>("/api/request-params?page={page}&size={size}", 2, 10)

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        // and
        val pageable = response.body ?: fail("body may not be null")
        assertThat(pageable.page).isEqualTo(2)
        assertThat(pageable.size).isEqualTo(10)
    }

    @Test
    fun `should get with query map`() {
        // when
        val response = restTemplate.getForEntity<Pageable>("/api/query-map?page={page}&size={size}", 3, 3)

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        // and
        val pageable = response.body ?: fail("body may not be null")
        assertThat(pageable.page).isEqualTo(3)
        assertThat(pageable.size).isEqualTo(3)
    }

    @Test
    fun `should get with pojo`() {
        // when
        val response = restTemplate.getForEntity<Pageable>(
            "/api/pojo?page={page}&size={size}&sortOrder={sortOrder}",
            5, 100, SortOrder.ASC
        )

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        // and
        val pageable = response.body ?: fail("body may not be null")
        assertThat(pageable.page).isEqualTo(5)
        assertThat(pageable.size).isEqualTo(100)
        assertThat(pageable.sortOrder).isEqualTo(SortOrder.ASC)
    }
}
