package com.shoploc.shoploc.domain.type;

import com.shoploc.shoploc.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProductServiceImpl implements TypeProductService {

    private TypeProductRepository typeProductRepository;

    public TypeProductServiceImpl (TypeProductRepository typeProductRepository) {
        this.typeProductRepository = typeProductRepository;
    }

    @Override
    public List<TypeProduct> getAllTypes() {
        return typeProductRepository.findAll();
    }

    @Override
    public TypeProduct createType(String libelle) {
        TypeProduct typeProduct = new TypeProduct();
        typeProduct.setLibelle(libelle);

        return typeProductRepository.save(typeProduct);

    }


}
