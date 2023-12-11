package com.shoploc.shoploc.domain.type;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/typeProduct")
public class TypeProductController {

    private TypeProductService typeProductService;

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
