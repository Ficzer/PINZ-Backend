package com.filipflorczyk.pinzbackend.services.impl;

import com.filipflorczyk.pinzbackend.entities.BaseEntity;
import com.filipflorczyk.pinzbackend.services.interfaces.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class BaseServiceImpl <TRepository extends JpaRepository<TModel, Long>, TModel extends BaseEntity>
        implements BaseService<TModel> {

    protected TRepository repository;
    protected ModelMapper modelMapper;

    public BaseServiceImpl(
            TRepository repository,
            ModelMapper modelMapper
    ) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TModel> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<TModel> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public TModel add(TModel model) {
        model.setVersion(0L);
        return repository.save(model);
    }

    @Override
    public void deleteById(Long id) {

        if(!repository.existsById(id)){
            throw entityNotFoundException("Entity", id);
        }

        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public TModel findById(Long id) {

        if(!repository.existsById(id)){
            throw entityNotFoundException("Entity", id);
        }

        return repository.findById(id)
                .orElseThrow(() -> entityNotFoundException("Entity", id));

    }

    protected EntityNotFoundException entityNotFoundException(String entity, Long id)  {
        return new EntityNotFoundException(entity + " with id " + id + " not found.");
    }

    protected EntityNotFoundException entityNotFoundException(String entity, String value) {
        return new EntityNotFoundException(entity + " with " + value + " doesn't exists.");
    }
}
