package cl.duoc.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.userms.entities.Laboratory;

// Repository layer for Laboratory
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {

}
