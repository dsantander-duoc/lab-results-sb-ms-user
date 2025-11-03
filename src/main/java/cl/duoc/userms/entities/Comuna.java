package cl.duoc.userms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comunas")
public class Comuna {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "comuna")
    private String comuna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id", insertable = false, updatable = false)
    private Provincia provincia;
}
