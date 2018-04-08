package com.sivalabs.techbuzz.service;

import com.sivalabs.techbuzz.entities.Link;
import com.sivalabs.techbuzz.repository.LinkRepository;
import com.sivalabs.techbuzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LinkService {

    private LinkRepository linkRepository;
    private UserRepository userRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public List<Link> getAllLinks() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdOn");
        return linkRepository.findAll(sort);
    }

    public Link createLink(Link link) {
        return linkRepository.save(link);
    }
}
