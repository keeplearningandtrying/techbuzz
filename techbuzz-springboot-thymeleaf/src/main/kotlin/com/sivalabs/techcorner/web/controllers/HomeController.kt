package com.sivalabs.techcorner.web.controllers

import com.sivalabs.techcorner.entities.Link
import com.sivalabs.techcorner.entities.Tag
import com.sivalabs.techcorner.repositories.LinkRepository
import com.sivalabs.techcorner.repositories.TagRepository
import com.sivalabs.techcorner.repositories.UserRepository
import com.sivalabs.techcorner.web.models.NewLinkDTO
import com.sivalabs.techcorner.web.models.SecurityUser
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

@Controller
class HomeController(val linkRepository: LinkRepository,
                     val tagRepository: TagRepository,
                     val userRepository: UserRepository) {

    @ModelAttribute("allTags")
    fun allTags() = tagRepository.findAll()

    @GetMapping("")
    fun home(model: Model) : String {
        val sort = Sort.by(Sort.Direction.DESC, "createdOn")
        model.addAttribute("links", linkRepository.findAll(sort))
        return "index"
    }

    @GetMapping("/tags/{name}")
    fun byTag(@PathVariable("name") tag : String, model: Model) : String {
        val tagOptional : Optional<Tag> = tagRepository.findByName(tag)
        if(tagOptional.isPresent) {
            model.addAttribute("links", tagOptional.get().links)
        } else {
            model.addAttribute("links", listOf<Link>())
        }
        model.addAttribute("searchTag", tag)
        return "index"
    }

    @GetMapping("/search")
    fun search(@RequestParam(name = "q", defaultValue = "") q: String, model: Model) : String {
        val sort = Sort.by(Sort.Direction.DESC, "createdOn")
        model.addAttribute("links", linkRepository.findByTitleContainingIgnoreCase(q, sort))
        model.addAttribute("q", q)
        return "index"
    }

    @PostMapping("/links")
    fun createLink(link: NewLinkDTO, @AuthenticationPrincipal loginUser: SecurityUser): String {
        saveLink(link, loginUser)
        return "redirect:/"
    }

    private fun saveLink(link: NewLinkDTO, loginUser: SecurityUser) {
        val newlink = Link()
        newlink.title = link.title
        newlink.url = link.url
        val tagsList = mutableListOf<Tag>()
        link.tags.split(",").forEach { tagName ->
            if (tagName.trim() != "") {
                val tag = createTagIfNotExist(tagName.trim())
                tagsList.add(tag)
            }
        }
        newlink.tags = tagsList
        val user = userRepository.findByEmail(loginUser.username)
        newlink.createdBy = user.get()
        linkRepository.save(newlink)
    }

    private fun createTagIfNotExist(tagName : String): Tag {
        val tagOptional = tagRepository.findByName(tagName)
        if (tagOptional.isPresent) {
            return tagOptional.get()
        }

        val tag = Tag()
        tag.name = tagName
        return tagRepository.save(tag)
    }
}