package com.sivalabs.techcorner.web.controllers

import com.sivalabs.techcorner.repositories.LinkRepository
import com.sivalabs.techcorner.web.models.LinkDTO
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LinkController(val linkRepository: LinkRepository) {

    @GetMapping("/api/links")
    fun links() : List<LinkDTO> {
        val sort = Sort.by(Sort.Direction.DESC, "createdOn")
        return linkRepository.findAll(sort).map { LinkDTO(it) }
    }
}