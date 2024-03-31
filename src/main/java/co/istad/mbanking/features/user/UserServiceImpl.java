package co.istad.mbanking.features.user;


import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.PasswordEditRequest;
import co.istad.mbanking.features.user.dto.UserEditRequest;
import co.istad.mbanking.features.user.dto.UserRequest;
import co.istad.mbanking.features.user.dto.UserDetailResponse;
import co.istad.mbanking.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public void createUser(UserRequest userCreateRequest) {


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
        if (userRepository.existsByStudentIdCard(userCreateRequest.studentId())) {
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

        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Role user has not been found"
                                ));
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
    public List<UserDetailResponse> findAllUser() {

        return userRepository.findAll().stream()
                .map(user -> new UserDetailResponse(
                        user.getUuid(),
                        user.getNationalCardId(),
                        user.getPhoneNumber(),
                        user.getName(),
                        user.getProfileImage(),
                        user.getGender(),
                        user.getDob()
                )).toList();

    }

    @Override
    public void editUserByUuid(String uuid, UserEditRequest request) {

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
}
