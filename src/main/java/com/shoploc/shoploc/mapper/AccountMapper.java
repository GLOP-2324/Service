package com.shoploc.shoploc.mapper;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    public AccountDTO toAccountDto(AccountEntity accountEntity);
    public AccountEntity toAccount(AccountDTO accountDTO);
}
