package cl.duoc.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.userms.entities.Role;

// Repository layer for Role
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
