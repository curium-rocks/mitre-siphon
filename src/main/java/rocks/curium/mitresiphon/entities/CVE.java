package rocks.curium.mitresiphon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
