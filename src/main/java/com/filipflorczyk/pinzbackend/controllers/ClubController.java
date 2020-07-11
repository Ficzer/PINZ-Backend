package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
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

@Validated
@RestController
public class ClubController {

    private ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                            Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<Club> spec = rootNode.accept(new CustomRsqlVisitor<>());
        Page<ClubDto> clubDtoPage = clubService.findAll(spec, pageable);

        return new ResponseEntity<>(clubDtoPage, HttpStatus.OK);
    }

    /**
     * Generates endpoint in player controller which returns all users
     * with attached links. This method has also enabled pagination options and generating links for
     * paged resources. Returned data are Dto's without internal information.
     *
     * @param pageable    pagination and sorting options given in url, {@link(Pageable)}
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing paged resource
     *                    in form of json+hal containing {@link(UserDto)}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<ClubDto> clubDtoPage = clubService.findAll(pageable);

        return new ResponseEntity<>(clubDtoPage, HttpStatus.OK);
    }

    /**
     * Generates end point in user controller which returns one user with given id
     * and correct links for this user. Returned data are Dto without internal information.
     *
     * @param  id         Given id number of specific user
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing
     *                    resource with {@link(UserDto)} and self link
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        ClubDto clubDto = clubService.findById(id);

        return new ResponseEntity(clubDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/clubs/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        clubService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/clubs")
    public ResponseEntity<?> addNewClub(@RequestBody NewClubDto newClubDto){

        clubService.addNewClub(newClubDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(produces = { "application/json" }, path = "/clubs/{id}")
    public ResponseEntity<?> updateClubInfo(@PathVariable Long id, @RequestBody ClubInfoDto clubInfoDto){

        clubService.updateClubInfo(id, clubInfoDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club")
    public ResponseEntity<?> getMyClub(){

        return new ResponseEntity(clubService.getMyClub(), HttpStatus.ACCEPTED);
    }
}

