package com.sivalabs.quicklinks.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.persistence.ManyToMany

@Entity
@Table(name = "tags")
class Tag {
    @Id
    @SequenceGenerator(name = "tag_generator", sequenceName = "tag_sequence", initialValue = 10)
    @GeneratedValue(generator = "tag_generator")
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var name: String = ""

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    var links: MutableList<Link> = mutableListOf()

    override fun toString(): String {
        return "Tag(id=$id, name='$name')"
    }


}