package com.filipflorczyk.pinzbackend.services.interfaces;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseService<TModel, UDto> {

    List<TModel> findAll();

    Page<TModel> findAll(Pageable pageable);

    Page<TModel> findAll(Specification<TModel> specification, Pageable pageable);

    TModel add(UDto model);

    void deleteById(Long id);

    void deleteAll();

    TModel findById(Long id);

    TModel convertToEntity(UDto dto);

    UDto convertToDto(TModel entity);
}
