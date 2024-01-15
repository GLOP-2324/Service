package com.shoploc.shoploc.domain.card;

import com.shoploc.shoploc.domain.account.AccountEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CardService {
    void createCard (AccountEntity accountEntity);
}
