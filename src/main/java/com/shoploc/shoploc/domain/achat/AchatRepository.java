package com.shoploc.shoploc.domain.achat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AchatRepository extends JpaRepository<AchatEntity,Integer> {
}
