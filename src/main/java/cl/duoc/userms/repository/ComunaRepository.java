package cl.duoc.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.userms.entities.Comuna;

// Repository layer for Comuna
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

}
