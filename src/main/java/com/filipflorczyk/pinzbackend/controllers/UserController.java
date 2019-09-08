package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(produces = { "application/hal+json" })
    public ResponseEntity<PagedResources<Resource<UserDto>>> getAll(@And({
                                        @Spec(path = "userName", spec = Like.class)
                                                        }) Specification<User> userSpec,
                                                        Pageable pageable,
                                            PagedResourcesAssembler assembler){

        Page<User> userPage = userService.findAll(userSpec, pageable);

        Page<UserDto> userDtoPage = addSelfLinksToPage(userPage);

        Link link = linkTo(UserController.class).withSelfRel();

        return new ResponseEntity<>(assembler.toResource(userDtoPage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = { "application/hal+json" })
    public ResponseEntity<UserDto> getOne(@PathVariable Long id){

        UserDto user = userService.convertToDto(userService.findById(id));

        user.add(linkTo(methodOn(UserController.class).getOne(id)).withSelfRel());

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping(produces = { "application/hal+json" })
    public ResponseEntity<HttpStatus> addOne(@RequestBody UserDto userDto){

        UserDto user = userService.convertToDto(userService.add(userDto));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    private Page<UserDto> addSelfLinksToPage(Page<User> userPage) {
        Page<UserDto> userDtoPage = userPage.map(userService::convertToDto);

        for(long i=0; i<userDtoPage.getNumberOfElements(); i++){
            userDtoPage.getContent().get((int)i).add(linkTo(methodOn(UserController.class).getOne(i)).withSelfRel());
        }

        return userDtoPage;
    }
}
