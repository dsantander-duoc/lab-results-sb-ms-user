package cl.duoc.userms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "regiones")
public class Region {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "abreviatura")
    private String abreviatura;

    @Column(name = "capital")
    private String capital;
}
