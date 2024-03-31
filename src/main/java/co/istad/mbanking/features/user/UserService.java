package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.PasswordEditRequest;
import co.istad.mbanking.features.user.dto.UserRequest;
import co.istad.mbanking.features.user.dto.UserDetailResponse;

import java.util.List;

public interface UserService {
    List<UserDetailResponse> findAllUser();

    void createUser(UserRequest userCreateRequest);
    void editPassword(String oldPassword, PasswordEditRequest request);
}
