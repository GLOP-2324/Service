package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
}
