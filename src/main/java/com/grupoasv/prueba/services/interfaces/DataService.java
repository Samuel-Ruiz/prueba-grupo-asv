package com.grupoasv.prueba.services.interfaces;

import java.util.List;

public interface DataService<S, T>{

    public S getById(final Integer id);

    public T[] getAll();

    public void update(final S dao);

    public void updateFromDto(final T[] dtos);

    public void create(final T dto);

    public List<S> getAllByStatus(final String status);
}
