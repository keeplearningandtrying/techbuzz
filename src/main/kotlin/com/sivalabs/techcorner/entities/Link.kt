package com.sivalabs.techcorner.entities

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
@Table(name = "links")
class Link {
    @Id
    @SequenceGenerator(name = "link_generator", sequenceName = "link_sequence", initialValue = 10)
    @GeneratedValue(generator = "link_generator")
    var id: Long? = null

    @Column(nullable = false)
    var title: String = ""

    @Column(nullable = false)
    var url: String = ""

    @ManyToMany(cascade = [(CascadeType.MERGE)])
    @JoinTable(name = "link_tag", joinColumns = [JoinColumn(name = "link_id", referencedColumnName = "ID")],
            inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "ID")])
    var tags: MutableList<Tag> = mutableListOf()

    @JoinColumn(nullable = false, name = "created_by")
    @ManyToOne()
    var createdBy: User = User()

    @Column(nullable = false)
    var createdOn: java.time.LocalDateTime = LocalDateTime.now()
}