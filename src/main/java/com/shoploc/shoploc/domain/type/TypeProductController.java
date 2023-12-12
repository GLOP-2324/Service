package com.shoploc.shoploc.domain.type;

import com.shoploc.shoploc.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public ResponseEntity<List<TypeProduct>> getAllTypes() {
        List<TypeProduct> types = typeProductService.getAllTypes();
        if(types != null ) {
            return new ResponseEntity<>(typeProductService.getAllTypes(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
