package com.sivalabs.techcorner.web.controllers

import com.sivalabs.techcorner.entities.User
import com.sivalabs.techcorner.repositories.LinkRepository
import com.sivalabs.techcorner.repositories.UserRepository
import com.sivalabs.techcorner.web.models.UserProfile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class UserController(val userRepository: UserRepository,
                     val linkRepository: LinkRepository,
                     val passwordEncoder: PasswordEncoder) {

    @GetMapping("/registration")
    fun showRegistrationForm(model: Model) : String {
        model.addAttribute("user", User())
        return "registration"
    }

    @PostMapping("/registration")
    fun register(@ModelAttribute("user") @Validated user: User,
                 errors: BindingResult,
                 redirectAttributes: RedirectAttributes) : String {
        if(errors.hasErrors()) {
            return "registration"
        }
        val userWithEmail = userRepository.findByEmail(user.email)
        if(userWithEmail.isPresent){
            errors.rejectValue("email","user.email.exist",
                    "User already registered with email ${user.email}")
            return "registration"
        }
        user.password = passwordEncoder.encode(user.password)
        user.role = "ROLE_USER"
        userRepository.save(user)
        redirectAttributes.addFlashAttribute("message", "Registration is successful")
        return "redirect:/registration_status"
    }

    @GetMapping("/users/{userId}")
    fun showUserProfile(@PathVariable("userId") userId: Long, model: Model) : String {
        val user = userRepository.findById(userId).get()
        val links = linkRepository.findByCreatedById(userId)
        val userProfile = UserProfile(user.id ?: 0, user.name, user.email, user.website, user.bio, links)
        model.addAttribute("userProfile", userProfile)
        return "userprofile"
    }
}