package co.istad.mbanking.features.user;

import co.istad.mbanking.base.BasedMessage;
import co.istad.mbanking.features.user.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserDetailsResponse> findAllUser(int page, int limit);
    UserDetailsResponse findByUuid(String uuid);

    void createUser(UserCreateRequest userCreateRequest);
    void editPassword(String oldPassword, PasswordEditRequest request);
    void updateUserByUuid(String uuid, UserEditRequest request);
    UserResponse updateUserByUuid(String uuid, UserUpdateRequest request);
    void deleteUserByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);
    BasedMessage enableByUuid(String uuid);
    BasedMessage disableByUuid(String uuid);
}
