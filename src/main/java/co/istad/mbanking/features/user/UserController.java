package co.istad.mbanking.features.user;


import co.istad.mbanking.base.BasedMessage;
import co.istad.mbanking.features.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.ScreenSleepEvent;
import java.io.PushbackReader;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<?> findAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int limit){
        return ResponseEntity.accepted().body(
                Map.of(
                        "users", userService.findAllUser(page, limit)
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createUser(@Valid @RequestBody UserCreateRequest request){
        userService.createUser(request);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/change-password/{oldPassword}")
    void editPassword(@PathVariable String oldPassword, @RequestBody PasswordEditRequest request){
        userService.editPassword(oldPassword, request);
    }

    @PatchMapping("uuid/{uuid}")
    void editUserByUuid(@PathVariable String  uuid,@RequestBody UserEditRequest request){
        userService.updateUserByUuid(uuid,request);
    }

    @PatchMapping("/{uuid}")
    UserResponse updateUserByUuid(@PathVariable String uuid, @RequestBody UserUpdateRequest request){
        return userService.updateUserByUuid(uuid, request);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{uuid}")
    UserDetailsResponse findUserByUuid(@PathVariable String uuid) {
        return userService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteUserByUuid(@PathVariable String uuid){
        userService.deleteUserByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/block")
    BasedMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/enable")
    BasedMessage enableByUuid(@PathVariable String uuid){
        return userService.enableByUuid(uuid);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/disable")
    BasedMessage disableByUuid(@PathVariable String uuid){
        return userService.disableByUuid(uuid);
    }

}
