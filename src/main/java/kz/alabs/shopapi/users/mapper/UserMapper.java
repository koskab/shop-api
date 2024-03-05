package kz.alabs.shopapi.users.mapper;

import kz.alabs.shopapi.users.dto.UserCreate;
import kz.alabs.shopapi.users.dto.UserUpdate;
import kz.alabs.shopapi.users.dto.UserView;
import kz.alabs.shopapi.users.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserCreate userCreate);

    User toEntity(@MappingTarget User entity, UserUpdate userUpdate);

    UserView toView(User user);

}
