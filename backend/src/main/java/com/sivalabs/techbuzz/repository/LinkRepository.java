package com.sivalabs.techbuzz.repository;

import com.sivalabs.techbuzz.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
