package xyz.andrewkboyd.mitresiphon.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Test data access object for the test table
 */
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
