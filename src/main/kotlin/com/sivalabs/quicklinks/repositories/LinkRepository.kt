package com.sivalabs.quicklinks.repositories

import com.sivalabs.quicklinks.entities.Link
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface LinkRepository : JpaRepository<Link, Long> {

    fun findByTitleContainingIgnoreCase(q: String, sort: Sort): List<Link>
}