package org.example.financemanager.mapper;

import org.example.financemanager.domain.User;
import org.example.financemanager.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
}
