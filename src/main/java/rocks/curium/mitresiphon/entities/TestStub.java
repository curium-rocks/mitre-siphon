package rocks.curium.mitresiphon.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/** Test data access object for the test table */
@Entity
@Table(name = "test")
public @Data class TestStub {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "title")
  private String title;
}
