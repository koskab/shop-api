package kz.alabs.shopapi.users.service.impl;

import kz.alabs.shopapi.core.exception.BadRequestException;
import kz.alabs.shopapi.core.exception.NotFoundException;
import kz.alabs.shopapi.core.security.JwtService;
import kz.alabs.shopapi.users.dto.*;
import kz.alabs.shopapi.users.entity.User;
import kz.alabs.shopapi.users.mapper.UserMapper;
import kz.alabs.shopapi.users.repository.UserRepository;
import kz.alabs.shopapi.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;


import static kz.alabs.shopapi.users.enums.Role.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    @Override
    public UserView findById(Long id) {
        User currentUser = getCurrentUser();
        if (currentUser.getId().equals(id) || currentUser.getRole() == ROLE_ADMIN)
            return UserMapper.INSTANCE.toView(getEntityById(id));
        throw new AccessDeniedException("Access denied");
    }

    @Transactional(readOnly = true)
    @Override
    public User getEntityByEmail(String email) {
        return repository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User with such email not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    @Override
    public UserEditResponse create(UserCreate userCreate) {
        if (!repository.existsByEmail(userCreate.getEmail())) {
            if (userCreate.getPassword().equals(userCreate.getRePassword())) {
                if (!userCreate.getBirthdate().isAfter(LocalDate.now())) {
                    userCreate.setPassword(passwordEncoder.encode(userCreate.getPassword()));
                    User user = UserMapper.INSTANCE.toEntity(userCreate);
                    user = repository.save(user);
                    return new UserEditResponse(user.getId());
                }
                throw new BadRequestException("Incorrect date of birth");
            }

            throw new BadRequestException("Passwords don't match");
        }

        throw new BadRequestException("User with this email already exists");
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        if(getEntityById(id).getRole() == ROLE_ADMIN)
            throw new AccessDeniedException("Access denied");

        repository.deleteById(id);
    }

    @Transactional
    @Override
    public UserEditResponse update(Long id, UserUpdate userUpdate) {
        User currentUser = getCurrentUser();
        User requestedUser = getEntityById(id);
        if (requestedUser == null)
            throw new NotFoundException("User not found");

        if (!userUpdate.getPassword().equals(userUpdate.getRePassword()))
            throw new BadRequestException("Passwords don't match");

        if (userUpdate.getBirthdate().isAfter(LocalDate.now().minusYears(16)))
            throw new BadRequestException("Incorrect date of birth");

        boolean hasAccess = id.equals(currentUser.getId()) || (currentUser.isAdmin()
                && !requestedUser.isAdmin());
        if(!hasAccess)
            throw new AccessDeniedException("Access denied");

        userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        User user = UserMapper.INSTANCE.toEntity(requestedUser, userUpdate);
        user = repository.save(user);
        return new UserEditResponse(user.getId());

    }

    @Transactional
    @Override
    public UserSignInResponse authenticate(UserSignIn request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = getEntityByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return UserSignInResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getEntityByEmail(username);
    }

    @Transactional
    @Override
    public UserEditResponse deposit(BigDecimal balance){
        if(balance.compareTo(BigDecimal.valueOf(100)) < 0)
            throw new BadRequestException("Minimal deposit amount is 100");
        User currentUser = getCurrentUser();
        currentUser.deposit(balance);
        return new UserEditResponse(currentUser.getId());
    }

}
