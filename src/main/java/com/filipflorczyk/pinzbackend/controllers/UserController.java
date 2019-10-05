package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.User;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import com.filipflorczyk.pinzbackend.tools.rsql_parsers.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Generates endpoint in user controller which returns all users passing RSQL filtering
     * with attached links. This method has also enabled pagination options and generating links for
     * paged resources. Returned data are Dto's without internal information.
     *
     * @param search      RSQL search filtering sentence
     * @param pageable    pagination and sorting options given in url, {@link(Pageable)}
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing paged resource
     *                    in form of json+hal containing {@link(UserDto)}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = { "application/json" }, path = "/users", params = {"search"})
    public ResponseEntity<Page<UserDto>> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        Page<UserDto> userPage = userService.findAll(spec, pageable);

        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * Generates endpoint in user controller which returns all users
     * with attached links. This method has also enabled pagination options and generating links for
     * paged resources. Returned data are Dto's without internal information.
     *
     * @param pageable    pagination and sorting options given in url, {@link(Pageable)}
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing paged resource
     *                    in form of json+hal containing {@link(UserDto)}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = { "application/json" }, path = "/users")
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable){

        Page<UserDto> userPage = userService.findAll(pageable);

        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * Generates end point in user controller which returns one user with given id
     * and correct links for this user. Returned data are Dto without internal information.
     *
     * @param  id         Given id number of specific user
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing
     *                    resource with {@link(UserDto)} and self link
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = { "application/json" }, path = "/users/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id){

        UserDto user = userService.findById(id);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     * Generates end point in user controller allows to add one validated user passed in
     * request body in form of json.
     *
     * @param  userDto    Given user
     * @return            Resource containing {@link(UserDto)} and self link
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(produces = { "application/json" }, path = "/users")
    public ResponseEntity<UserDto> addOne(@Valid @RequestBody UserDto userDto){

        UserDto user = userService.add(userDto);

        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(produces = { "application/json" }, path = "/users/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        userService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(produces = { "application/json" }, path = "/users/me")
    public ResponseEntity<UserDto> getMe(){

        UserDto user = userService.getCurrentUserDto();

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
