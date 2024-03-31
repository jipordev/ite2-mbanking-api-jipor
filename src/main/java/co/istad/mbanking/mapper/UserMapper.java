package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.UserRequest;
import co.istad.mbanking.features.user.dto.UserDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // SourceType = UserCreateRequest (parameter)
    // TargetType = User (ReturnType)
    User fromUserCreateRequest(UserRequest userCreateRequest);

    // void fromUserCreateRequest2(@MappingTarget User user, UserCreateRequest userCreateRequest);

    UserDetailResponse toUserDetailResponse(User user);

}
