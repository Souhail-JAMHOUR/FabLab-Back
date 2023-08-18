package ma.odc.fablabback.mappers;

import ma.odc.fablabback.dto.users.AppUserDTO;
import ma.odc.fablabback.entities.Users.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AppUserDTO appUserToDTO(AppUser appUser);
    
}