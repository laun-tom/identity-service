package com.own.identity_service.mapper;

import com.own.identity_service.domain.User;
import com.own.identity_service.dto.OAuth2Profile;
import com.own.identity_service.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDTO);

    User toUser(OAuth2Profile oAuth2Profile);
}
