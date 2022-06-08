package com.fastcampus.kopring.issueservice.domain

import javax.persistence.*

@Entity
@Table
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    val issue: Issue,

    @Column
    var userId: Long,


    @Column
    var body: String,
) : BaseEntity()