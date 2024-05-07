package com.example.crud.mapper;

import java.util.List;

public interface EntityMapper <D, E>{
    E toEntity(D dto);
    D toDto(E entity);
    List<D> toDto(List<E> entity);
    List<E> toEntity(List<D> dto);
}
