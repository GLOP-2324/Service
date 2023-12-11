package com.shoploc.shoploc.domain.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/typeProduct")
public class TypeProductController {

    private TypeProductService typeProductService;

    @Autowired
    public TypeProductController(TypeProductService typeProductService) {
        this.typeProductService = typeProductService;
    }

    @PostMapping("/")
    public ResponseEntity<TypeProduct> createTypeProduct(@RequestBody TypeProduct typeProduct) {
        TypeProduct createdTypeProduct = typeProductService.createType(
                typeProduct.getLibelle()
        );
        return new ResponseEntity<>(createdTypeProduct, HttpStatus.CREATED);
    }

}
