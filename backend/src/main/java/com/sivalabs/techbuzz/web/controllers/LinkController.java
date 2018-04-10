package com.sivalabs.techbuzz.web.controllers;

import com.sivalabs.techbuzz.entities.Link;
import com.sivalabs.techbuzz.entities.User;
import com.sivalabs.techbuzz.security.SecurityUtils;
import com.sivalabs.techbuzz.service.LinkService;
import com.sivalabs.techbuzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( value = "/api")
public class LinkController {

    private final LinkService linkService;
    private final UserService userService;
    private final SecurityUtils securityUtils;

    @Autowired
    public LinkController(LinkService linkService, UserService userService, SecurityUtils securityUtils) {
        this.linkService = linkService;
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/links")
    public List<Link> getAllLinks() {
        return linkService.getAllLinks();
    }

    @PostMapping("/links")
    @PreAuthorize("hasRole('USER')")
    public Link createLink(@RequestBody @Valid Link link)
    {
        User user = securityUtils.getLoginUser();
        link.setCreatedBy(user);
        return linkService.createLink(link);
    }
}
