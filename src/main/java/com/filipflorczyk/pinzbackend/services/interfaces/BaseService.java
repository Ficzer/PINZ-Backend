package com.filipflorczyk.pinzbackend.services.interfaces;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseService<TModel, UDto> {

    /**
     * Generic method inherited from basic service class. It gets all elements from data base
     * and place it in list
     *
     * @return {@link(List} of all elements of given type form database
     */
    List<TModel> findAll();

    /**
     * Generic method inherited from basic service class. It gets all elements from data base
     * and place it in page
     *
     * @param pageable      {@link(Pageable)} which represents information about pagination and sorting
     * @return              {@link(Page} of all elements of given type form database
     */
    Page<TModel> findAll(Pageable pageable);

    /**
     * Generic method inherited from basic service class. It gets all elements from data base
     * and place it in page
     *
     * @param pageable      {@link(Pageable)} which represents information about pagination and sorting
     * @param specification {@link(Specyfication)} which represents information about element's filtering
     * @return              {@link(Page} of all elements of given type form database
     */
    Page<TModel> findAll(Specification<TModel> specification, Pageable pageable);

    /**
     * Generic method inherited from basic service class. It places element passed in parameter in data base
     *
     * @param model         Dto of given element, which will be save in data base
     * @return              {@link(Page} of all elements of given type form database
     */
    UDto add(UDto model);

    /**
     * Generic method inherited from basic service class. It deletes element in data based based on given id
     *
     * @param id            Id of element to delete
     */
    void deleteById(Long id);

    /**
     * Generic method inherited from basic service class. It deletes all elements of given type in data base
     *
     */
    void deleteAll();

    /**
     * Generic method inherited from basic service class. It returns element from data based on given id
     *
     * @param id            Id of element to find
     * @return              Dto of element found in data base based on given id
     */
    UDto findById(Long id);

    /**
     * Generic method inherited from basic service class. It converts entities which build relational model in data base
     * to theirs Dto equivalent
     *
     * @param dto           Passed Dto
     * @return              Entity of given Dto
     */
    TModel convertToEntity(UDto dto);

    /**
     * Generic method inherited from basic service class. It converts Dto
     * to theirs entity equivalent
     *
     * @param entity        Passed entity
     * @return              Dto of given entity
     */
    UDto convertToDto(TModel entity);
}
