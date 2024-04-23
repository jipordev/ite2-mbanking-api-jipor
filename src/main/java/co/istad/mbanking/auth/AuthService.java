package co.istad.mbanking.auth;

import co.istad.mbanking.auth.dto.AuthResponse;
import co.istad.mbanking.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
}
