package com.sivalabs.quicklinks.web.controllers

import com.sivalabs.quicklinks.entities.Tag
import com.sivalabs.quicklinks.repositories.TagRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TagController(val tagRepository: TagRepository) {

    @GetMapping("/api/tags")
    fun tags() : List<Tag> {
        return tagRepository.findAll()
    }
}