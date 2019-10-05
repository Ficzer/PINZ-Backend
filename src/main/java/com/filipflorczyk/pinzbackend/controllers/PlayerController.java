package com.filipflorczyk.pinzbackend.controllers;

import com.filipflorczyk.pinzbackend.dtos.BooleanDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerInfoDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerStatsDto;
import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.entities.Player;
import com.filipflorczyk.pinzbackend.services.interfaces.ClubService;
import com.filipflorczyk.pinzbackend.services.interfaces.PlayerService;
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

@Validated
@RestController
public class PlayerController {

    private PlayerService playerService;
    private ClubService clubService;

    public PlayerController(PlayerService playerService, ClubService clubService){
        this.playerService = playerService;
        this.clubService = clubService;
    }

    /**
     * Generates endpoint in user controller which returns all players passing RSQL filtering
     * with attached links. This method has also enabled pagination options and generating links for
     * paged resources. Returned data are Dto's without internal information.
     *
     * @param search      RSQL search filtering sentence
     * @param pageable    pagination and sorting options given in url, {@link(Pageable)}
     * @return            {@linkResponseEntity} with HTTP Ok status and body containing paged resource
     *                    in form of json+hal containing {@link(UserDto)}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/players", params = {"search"})
    public ResponseEntity<?> getAllWithRsql(@RequestParam(value = "search", required = false) String search,
                                                        Pageable pageable){

        Node rootNode = new RSQLParser().parse(search);
        Specification<Player> spec = rootNode.accept(new CustomRsqlVisitor<Player>());
        Page<PlayerDto> playerPage = playerService.findAll(spec, pageable);

        return new ResponseEntity<>(playerPage, HttpStatus.OK);
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
    @GetMapping(produces = { "application/json" }, path = "/players")
    public ResponseEntity<?> getAll(Pageable pageable){

        Page<PlayerDto> playerPage = playerService.findAll(pageable);

        return new ResponseEntity<>(playerPage, HttpStatus.OK);
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
    @GetMapping(produces = { "application/json" }, path = "/players/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id){

        PlayerDto playerDto = playerService.findById(id);

        return new ResponseEntity(playerDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @DeleteMapping(produces = { "application/json" }, path = "/players/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){

        playerService.deleteById(id);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PostMapping(produces = { "application/json" }, path = "/players/{id}/is-trainer")
    public ResponseEntity<?> makePlayerTrainer(@PathVariable Long id, @RequestBody BooleanDto booleanDto){

        return new ResponseEntity(playerService.makePlayerTrainer(id, booleanDto), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = {"application/json"}, path = "/users/me/player")
    public ResponseEntity<?> getMyPlayer(){

        return new ResponseEntity<>(playerService.getMyPlayer(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PostMapping(produces = {"application/json"}, path = "/users/me/player")
    public ResponseEntity<?> addMyPlayer(@RequestBody @Valid PlayerDto playerDto){

        return new ResponseEntity<>(playerService.addMyPlayer(playerDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @PutMapping(produces = {"application/json"}, path = "/users/me/player")
    public ResponseEntity<?> updateMyPlayer(@RequestBody @Valid PlayerInfoDto playerDto){

        return new ResponseEntity<>(playerService.updateMyPlayerInformation(playerDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @PutMapping(produces = {"application/json"}, path = "players/{id}")
    public ResponseEntity<?> updatePlayerStats(@PathVariable Long id, @RequestBody @Valid PlayerStatsDto playerDto){

        return new ResponseEntity<>(playerService.updatePlayerStats(id, playerDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @DeleteMapping(produces = { "application/json" }, path = "/clubs/my-club/players/{id}")
    public ResponseEntity<?> deletePlayerFromMyClub(@PathVariable Long id){

        return new ResponseEntity(clubService.removePlayerFromMyClub(id), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR', 'USER')")
    @GetMapping(produces = { "application/json" }, path = "/clubs/my-club/players")
    public ResponseEntity<?> getMyClubPlayers(Pageable pageable){

        return new ResponseEntity(playerService.getPlayersOfMyClub(pageable), HttpStatus.ACCEPTED);
    }
}
