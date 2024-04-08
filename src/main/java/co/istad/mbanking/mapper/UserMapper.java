package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.User;
import co.istad.mbanking.domain.UserAccount;
import co.istad.mbanking.features.user.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // SourceType = UserCreateRequest (parameter)
    // TargetType = User (ReturnType)
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    User fromUserEditRequest(UserEditRequest request);

    // void fromUserCreateRequest2(@MappingTarget User user, UserCreateRequest userCreateRequest);

    UserDetailsResponse toUserDetailResponse(User user);

    List<UserDetailsResponse> toListUserDetailResponse(List<User> users);

//    List<UserDetailsResponse> toListUserDetailResponse(List<User> users);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUerUpdateRequest(UserUpdateRequest request,@MappingTarget User user);

    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccounts) {
        return toUserResponse(userAccounts.get(0).getUser());
    }

}
