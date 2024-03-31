package co.istad.mbanking.features.user;


import co.istad.mbanking.features.user.dto.PasswordEditRequest;
import co.istad.mbanking.features.user.dto.UserEditRequest;
import co.istad.mbanking.features.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<?> findAllUsers(){
        return ResponseEntity.accepted().body(
                Map.of(
                        "users", userService.findAllUser()
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createUser(@Valid @RequestBody UserRequest request){
        userService.createUser(request);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/change-password/{oldPassword}")
    void editPassword(@PathVariable String oldPassword, @RequestBody PasswordEditRequest request){
        userService.editPassword(oldPassword, request);
    }

    @PatchMapping("uuid/{uuid}")
    void editUserByUuid(@PathVariable String  uuid,@RequestBody UserEditRequest request){
        userService.editUserByUuid(uuid,request);
    }

}
