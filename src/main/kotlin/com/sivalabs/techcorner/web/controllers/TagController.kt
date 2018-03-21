package com.sivalabs.techcorner.web.controllers

import com.sivalabs.techcorner.entities.Tag
import com.sivalabs.techcorner.repositories.TagRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TagController(val tagRepository: TagRepository) {

    @GetMapping("/api/tags")
    fun tags() : List<Tag> {
        return tagRepository.findAll()
    }
}