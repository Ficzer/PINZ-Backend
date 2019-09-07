package com.filipflorczyk.pinzbackend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<TModel> {

    List<TModel> findAll();

    Page<TModel> findAllPaged(Pageable pageable);

    TModel add(TModel model);

    void deleteById(Long id);

    void deleteAll();

    TModel findById(Long id);
}
