package com.shoploc.shoploc.domain.product;

import com.shoploc.shoploc.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById (Long id);

    void deleteById (Long id);

}
