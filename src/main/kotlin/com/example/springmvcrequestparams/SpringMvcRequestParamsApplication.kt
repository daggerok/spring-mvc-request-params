package com.example.springmvcrequestparams

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

enum class SortOrder {
    ASC,
    DESC,
}

data class Pageable(
    val page: Int = 1,
    val size: Int = 25,
    val sortBy: String = "createdAt",
    val sortOrder: SortOrder = SortOrder.DESC,
)

fun Map<String, String>.toPageable(): Pageable = let {
    Pageable(
        page = it["page"]?.toInt() ?: 1,
        size = it["size"]?.toInt() ?: 25,
        sortBy = it["sortBy"] ?: "createdAt",
        sortOrder = SortOrder.valueOf(it["sortOrder"] ?: SortOrder.DESC.name),
    )
}

@RestController
class PageableResource {

    @GetMapping("/api/request-param")
    fun requestParams(
        @RequestParam(name = "page", defaultValue = "1") page: Int,
        @RequestParam(name = "size", defaultValue = "25") size: Int,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") sortBy: String,
        @RequestParam(name = "sortOrder", defaultValue = "DESC") sortOrder: SortOrder
    ) = Pageable(page, size, sortBy, sortOrder)

    @GetMapping("/api/query-map")
    fun requestParams(@RequestParam query: Map<String, String>) =
        query.toPageable()

    @GetMapping("/api/pojo")
    fun requestParams(pageable: Pageable) = pageable
}

@SpringBootApplication
class SpringMvcRequestParamsApplication

fun main(args: Array<String>) {
    runApplication<SpringMvcRequestParamsApplication>(*args)
}
