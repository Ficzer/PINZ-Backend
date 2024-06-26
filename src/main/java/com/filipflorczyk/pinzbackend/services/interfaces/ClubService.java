package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.entities.Club;

public interface ClubService extends BaseService<Club, ClubDto> {

    void addNewClub(NewClubDto newClubDto);

    ClubDto getMyClub();

    void updateClubInfo(Long id, ClubInfoDto clubInfoDto);

    void updateMyClubInfo(ClubInfoDto clubInfoDto);

    void removePlayerFromMyClub(Long id);


}
