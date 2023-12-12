package com.shoploc.shoploc.domain.type;

import java.util.List;

public interface TypeProductService {

    List<TypeProduct> getAllTypes();
    TypeProduct createType(String libelle);

}
