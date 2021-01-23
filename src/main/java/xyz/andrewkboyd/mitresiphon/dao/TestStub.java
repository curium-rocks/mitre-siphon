package xyz.andrewkboyd.mitresiphon.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "test")
public @Data
class TestStub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;
}
