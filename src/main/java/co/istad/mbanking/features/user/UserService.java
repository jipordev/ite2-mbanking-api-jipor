package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.UserCreateRequest;

public interface UserService {

    void createUser(UserCreateRequest userCreateRequest);
}
