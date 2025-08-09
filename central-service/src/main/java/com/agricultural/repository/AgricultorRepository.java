package edu.ec.com.central.repository;

import edu.ec.com.central.model.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AgricultorRepository extends JpaRepository<Agricultor, UUID> {}
