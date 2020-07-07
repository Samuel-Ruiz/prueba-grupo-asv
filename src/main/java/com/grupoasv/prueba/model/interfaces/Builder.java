package com.grupoasv.prueba.model.interfaces;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Builder<S, T>{

    public abstract S buildDao(final T dto);

    public List<S> buildDao(final List<T> dtoList){
        return dtoList.stream().map(this::buildDao).collect(Collectors.toList());
    }

    public abstract T buildDto(final S dao);

    public List<T> buildDto(final List<S> daoList){
        return daoList.stream().map(this::buildDto).collect(Collectors.toList());
    }

}
