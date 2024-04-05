package com.shoploc.shoploc.domain.type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeProductRepository extends JpaRepository<TypeProduct,Integer> {

    Optional<TypeProduct> findById (Integer id);
}
