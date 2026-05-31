package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user in the system.
     * Throws an exception if the user already has an assigned ID.
     *
     * @param user the user entity to be created
     * @return the persisted user entity
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);

        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }

        return userRepository.save(user);
    }

    /**
     * Retrieves a user entity by its ID.
     *
     * @param userId the user identifier
     * @return optional containing the user entity if found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Searches users by email fragment (case-insensitive).
     *
     * @param email email fragment used for searching
     * @return list of matching users as DTOs
     */
    @Override
    public List<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Retrieves a user by its ID and converts it to DTO.
     *
     * @param id the user identifier
     * @return user data as DTO
     */
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        return userMapper.toUserDto(user);
    }

    /**
     * Retrieves all users as entity objects.
     *
     * @return list of all users (entities)
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves all users and maps them to DTOs.
     *
     * @return list of users as DTOs
     */
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Retrieves users older than the specified date.
     *
     * @param date birthdate threshold used for filtering
     * @return list of users older than the given date as DTOs
     */
    @Override
    public List<UserDto> getUsersOlderThan(LocalDate date) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Updates an existing user with new data.
     *
     * @param id the user identifier
     * @param userDto the new user data
     * @return updated user as DTO
     */
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.update(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );

        return userMapper.toUserDto(userRepository.save(user));
    }

    /**
     * Deletes a user from the system by ID.
     *
     * @param id the user identifier
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

