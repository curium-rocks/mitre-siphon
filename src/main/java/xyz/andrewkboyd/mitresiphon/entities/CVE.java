package xyz.andrewkboyd.mitresiphon.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "cve")
public @Data class CVE {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "ref_url")
    private String references;
}
