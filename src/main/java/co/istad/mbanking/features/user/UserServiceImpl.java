package co.istad.mbanking.features.user;


import co.istad.mbanking.base.BasedMessage;
import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.Transaction;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.*;
import co.istad.mbanking.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${MEDIA_BASE_URI}")
    private String mediaBaseUri;


    @Override
    public void createUser(UserCreateRequest userCreateRequest) {


        if (userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number is already existed"
            );
        }
        if (userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card is already existed"
            );
        }
        if (userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student card is already existed"
            );
        }
        if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match!"
            );
        }

        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Role user has not been found"
                                ));

        // Create dynamic role from client
        userCreateRequest.roles().forEach(r -> {
            Role newRole = roleRepository.findByName(r.name())
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Role USER has not been found!"));
            roles.add(newRole);
        });


        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void editPassword(String oldPassword, PasswordEditRequest request) {

        if (!oldPassword.equals(request.password())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong password! Please try again"
            );
        }
        if (!request.newPassword().equals(request.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match!"
            );
        }
        User user = userRepository.findAll().stream().findFirst().orElseThrow();
        user.setPassword(request.newPassword());
        userRepository.save(user);
    }

    @Override
    public Page<UserDetailsResponse> findAllUser(int page, int limit) {
        // Create pageRequest object
        PageRequest pageRequest = PageRequest.of(page, limit);
        // Invoke findAll(pageRequest)
        Page<User> users = userRepository.findAll(pageRequest);
        // Map result
        return users.map(userMapper::toUserDetailResponse);

    }

    @Override
    public UserDetailsResponse findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user has not been found"
                )
        );
        return userMapper.toUserDetailResponse(user);
    }

    @Override
    public void updateUserByUuid(String uuid, UserEditRequest request) {

        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }
        User user = userRepository.findAll().stream()
                .filter(user1 -> user1.getUuid().equals(uuid))
                .findFirst().orElseThrow();
        user.setCityOrProvince(request.cityOrProvince());
        user.setKhanOrDistrict(request.khanOrDistrict());
        user.setSangkatOrCommune(request.sangkatOrCommune());
        user.setEmployeeType(request.employeeType());
        user.setPosition(request.position());
        user.setCompanyName(request.companyName());
        user.setMainSourceOfIncome(request.mainSourceOfIncome());
        user.setMonthlyIncomeRange(request.monthlyIncomeRange());
        userRepository.save(user);

    }

    @Override
    public UserResponse updateUserByUuid(String uuid, UserUpdateRequest request) {

        // check uuid if it's existed
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user has not been found"
                )
        );

        userMapper.fromUerUpdateRequest(request, user);

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserByUuid(String uuid) {

        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user has not been found"
                )
        );

        userRepository.delete(user);

    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }
        userRepository.blockByUuid(uuid);

        return new BasedMessage("User has been blocked");
    }

    @Transactional
    @Override
    public BasedMessage enableByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }
        userRepository.enableByUuid(uuid);

        return new BasedMessage("User has been enabled");
    }

    @Transactional
    @Override
    public BasedMessage disableByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }
        userRepository.disableByUuid(uuid);

        return new BasedMessage("User has been disabled");
    }

    @Override
    public String updateProfileImage(String uuid, String mediaName) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found!"
            );
        }
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "User has not been found!"
                    )
        );
        user.setProfileImage(mediaName);
        userRepository.save(user);

        return mediaBaseUri + "IMAGE/" + mediaName;
    }
}
