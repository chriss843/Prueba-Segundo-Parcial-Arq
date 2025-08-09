package edu.ec.com.central.repository;

import edu.ec.com.central.model.Cosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CosechaRepository extends JpaRepository<Cosecha, UUID> {}
