package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.util.List;

@Repository
public interface HistoriqueAchatRepository extends JpaRepository<HistoriqueAchat,Long> {
    List<HistoriqueAchat> findByClientEmail(@Param("clientEmail") String clientEmail);
    List<HistoriqueAchat> findAllByClientEmailAndPurchaseDateAfter(String clientEmail, LocalDate localDate);

}
