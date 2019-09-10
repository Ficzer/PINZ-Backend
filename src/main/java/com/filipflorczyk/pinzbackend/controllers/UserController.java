package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import com.filipflorczyk.pinzbackend.tools.rsql_parsers.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    @GetMapping(produces = { "application/hal+json" }, params = {"search", "page", "size"})
//    public ResponseEntity<PagedResources<Resource<UserDto>>> getAllWithRsqlAndPagination(@RequestParam(value = "search", required = false) String search,
//                                                                            Pageable pageable){
//
//        Node rootNode = new RSQLParser().parse(search);
//        Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
//        Page<User> userPage = userService.findAll(spec, pageable);
//
//        Page<UserDto> userDtoPage = addSelfLinksToPageContent(userPage);
//
//        Link link = linkTo(methodOn(UserController.class).getAllWithRsqlAndPagination(search, pageable)).withSelfRel();
//
//        return new ResponseEntity<>(assembler.toResource(userDtoPage, link), HttpStatus.OK);
//    }

    @GetMapping(produces = { "application/hal+json" }, params = {"search"})
    public ResponseEntity<PagedResources<Resource<UserDto>>> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                                                            Pageable pageable,
                                                                            PagedResourcesAssembler assembler){

        Node rootNode = new RSQLParser().parse(search);
        Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        Page<User> userPage = userService.findAll(spec, pageable);

        Page<UserDto> userDtoPage = addSelfLinksToPageContent(userPage);

        return new ResponseEntity<>(assembler.toResource(userDtoPage), HttpStatus.OK);
    }

    @GetMapping(produces = { "application/hal+json" })
    public ResponseEntity<PagedResources<Resource<UserDto>>> getAll(Pageable pageable,
                                                                    PagedResourcesAssembler assembler){
        Page<User> userPage = userService.findAll(pageable);

        Page<UserDto> userDtoPage = addSelfLinksToPageContent(userPage);

        return new ResponseEntity<>(assembler.toResource(userDtoPage), HttpStatus.OK);
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

    private Page<UserDto> addSelfLinksToPageContent(Page<User> userPage) {

        final List<Long> idList = new ArrayList<>();
        userPage.forEach(user -> {idList.add(user.getId());});

        Page<UserDto> userDtoPage = userPage.map(userService::convertToDto);

        for(long i=0; i<userDtoPage.getNumberOfElements(); i++){
            userDtoPage.getContent().get((int)i).add(linkTo(methodOn(UserController.class).getOne(idList.get((int)i))).withSelfRel());
        }

        return userDtoPage;
    }
}
