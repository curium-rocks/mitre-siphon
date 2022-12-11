package rocks.curium.mitresiphon.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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
