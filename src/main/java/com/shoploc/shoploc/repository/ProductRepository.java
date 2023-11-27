package com.shoploc.shoploc.repository;

import com.shoploc.shoploc.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findById (Long id);

    void deleteById (Long id);

}
