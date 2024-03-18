package com.shoploc.shoploc.domain.historique;

import com.shoploc.shoploc.domain.achat.AchatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoriqueAchatRepository extends JpaRepository<HistoriqueAchat,Integer> {

    List<HistoriqueAchat> findAllByClientEmailAndPurchaseDateAfter(String clientEmail, LocalDate localDate);
}
