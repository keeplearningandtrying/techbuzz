package com.sivalabs.techcorner.repositories

import com.sivalabs.techcorner.entities.Link
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface LinkRepository : JpaRepository<Link, Long> {

    fun findByTitleContainingIgnoreCase(q: String, sort: Sort): List<Link>
}