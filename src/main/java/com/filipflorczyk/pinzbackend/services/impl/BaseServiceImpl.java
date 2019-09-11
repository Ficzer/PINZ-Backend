package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.entities.BaseEntity;
import com.filipflorczyk.pinzbackend.services.interfaces.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class BaseServiceImpl
        <TRepository extends JpaRepository<TModel, Long> & JpaSpecificationExecutor<TModel>, TModel extends BaseEntity, UDto>
        implements BaseService<TModel, UDto> {

    protected TRepository repository;
    protected ModelMapper modelMapper;

    public BaseServiceImpl(
            TRepository repository,
            ModelMapper modelMapper
    ) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    protected EntityNotFoundException entityNotFoundException(Long id, String name) {
        return new EntityNotFoundException(name + " with id " + id + " not found.");
    }

    protected EntityNotFoundException entityNotFoundException(String entity, String value) {
        return new EntityNotFoundException(entity + " with " + value + " doesn't exists.");
    }

    @Override
    public List<TModel> findAll() {
        List<TModel> modelList = repository.findAll();
        return modelList;
    }

    @Override
    public Page<TModel> findAll(Pageable pageable) {
        Page<TModel> modelPage = repository.findAll(pageable);
        return modelPage;
    }

    @Override
    public Page<TModel> findAll(Specification<TModel> specification, Pageable pageable) {
        Page<TModel> modelPage = repository.findAll(specification, pageable);
        return modelPage;
    }

    @Override
    public UDto add(UDto dto) {
        TModel savedEntity = repository.save(convertToEntity(dto));
        savedEntity.setVersion(0L);
        return convertToDto(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw entityNotFoundException(id, "Entity");
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public UDto findById(Long id) {
        TModel model = repository
                .findById(id)
                .orElseThrow(() -> entityNotFoundException(id, "Entity"));
        return convertToDto(model);
    }

    @Override
    public TModel convertToEntity(UDto dto) {
        throw new UnsupportedOperationException("Method must be implemented in super class");
    }

    @Override
    public UDto convertToDto(TModel entity) {
        throw new UnsupportedOperationException("Method must be implemented in super class");
    }
}

