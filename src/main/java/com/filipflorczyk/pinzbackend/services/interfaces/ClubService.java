package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubInfoDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.NewClubDto;
import com.filipflorczyk.pinzbackend.entities.Club;

public interface ClubService extends BaseService<Club, ClubDto> {

    ClubDto addNewClub(NewClubDto newClubDto);

    ClubDto getMyClub();

    ClubDto updateClubInfo(Long id, ClubInfoDto clubInfoDto);

    ClubDto updateMyClubInfo(ClubInfoDto clubInfoDto);

    ClubDto removePlayerFromMyClub(Long id);


}
