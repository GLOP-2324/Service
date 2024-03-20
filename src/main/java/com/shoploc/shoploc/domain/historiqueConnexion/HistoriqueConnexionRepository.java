package com.shoploc.shoploc.domain.historiqueConnexion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueConnexionRepository extends JpaRepository<HistoriqueConnexion,Long> {}
