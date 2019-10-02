package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.UserRoleDto;
import com.filipflorczyk.pinzbackend.entities.UserRole;
import com.filipflorczyk.pinzbackend.services.interfaces.UserRoleService;
import com.filipflorczyk.pinzbackend.tools.rsql_parsers.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserRoleController {

    UserRoleService userRoleService;

    public UserRoleController(UserRoleService userService){
        this.userRoleService = userService;
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
    @GetMapping(produces = { "application/json" }, path = "/user-roles", params = {"search"})
    public ResponseEntity<Page<UserRoleDto>> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<UserRole> spec = rootNode.accept(new CustomRsqlVisitor<UserRole>());
        Page<UserRoleDto> userPage = userRoleService.findAll(spec, pageable);

        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * Generates endpoint in user controller which returns all users passing RSQL filtering
     * with attached links. This method has also enabled pagination options and generating links for
     * paged resources. Returned data are Dto's without internal information.
     *
     * @param pageable    pagination and sorting options given in url, {@link(Pageable)}
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing paged resource
     *                    in form of json+hal containing {@link(UserDto)}
     */
    @GetMapping(produces = { "application/json" }, path = "/user-roles")
    public ResponseEntity<Page<UserRoleDto>> getAll(Pageable pageable){

        Page<UserRoleDto> userPage = userRoleService.findAll(pageable);

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
    @GetMapping(produces = { "application/json" }, path = "/user-roles/{id}")
    public ResponseEntity<UserRoleDto> getOne(@PathVariable Long id){

        UserRoleDto userRole = userRoleService.findById(id);

        return new ResponseEntity(userRole, HttpStatus.OK);
    }

    @PostMapping(produces = { "application/json" }, path = "/user-roles")
    public ResponseEntity<UserRoleDto> addOne(@RequestBody @Valid UserRoleDto userRoleDto){

        UserRoleDto userRole = userRoleService.add(userRoleDto);

        return new ResponseEntity(userRole, HttpStatus.CREATED);
    }

    @DeleteMapping(produces = { "application/json" }, path = "/user-roles/{id}")
    public ResponseEntity<UserRoleDto> deleteOne(@PathVariable Long id){

        userRoleService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = { "application/json" }, path = "/users/{id}/user-roles")
    public ResponseEntity<?> getRolesOfUser(@PathVariable Long id, Pageable pageable){

        Page<UserRoleDto> userRoleDtoPage = userRoleService.findRolesOfUser(id, pageable);

        return new ResponseEntity(userRoleDtoPage, HttpStatus.OK);
    }

    @PostMapping(produces = { "application/json" }, path = "/users/{id}/user-roles")
    public ResponseEntity<UserRoleDto> addRoleToUser(@PathVariable Long id,
                                                     @RequestBody @Valid BaseDto userRoleDto){

        UserRoleDto userRole = userRoleService.addRolesToUser(id, userRoleDto);

        return new ResponseEntity(userRole, HttpStatus.OK);
    }
}
