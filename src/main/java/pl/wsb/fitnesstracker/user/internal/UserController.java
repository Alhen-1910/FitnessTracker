package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    private final UserProvider userProvider;

    private final UserMapper userMapper;

    /**
     * Creates new user in system.
     *
     * @param userDto user data
     * @return created user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // TODO: Implement the method to add a new user.
        //  You can use the @RequestBody annotation to map the request body to the UserDto object.

        User user = this.userMapper.toUser(userDto);

        User createdUser = this.userService.createUser(user);

        return this.userMapper.toUserDto(createdUser);
    }

    /**
     * Returns a list of all users in the system.
     * The method uses a provider and mapper to convert entities into DTOs.
     *
     * @return list of users as DTOs
     */
    @GetMapping
    public List<UserDto> getUsers() throws InterruptedException {

        return this.userProvider.findAllUsers().stream()
                .map(this.userMapper::toUserDto)
                .toList();
    }

    /**
     * Returns a simplified list of users.
     * Delegates the call directly to the service layer.
     *
     * @return list of users as DTOs
     */
    @GetMapping("/simple")
    public List<UserDto> getSimpleUsers() {
        return userService.getUsers();
    }

    /**
     * Retrieves detailed information about a user by their ID.
     *
     * @param id the user identifier
     * @return user data as DTO
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Searches for users by email address (case-insensitive, partial match).
     *
     * @param email email fragment used for searching
     * @return list of users matching the criteria
     */
    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Returns users older than the specified date.
     *
     * @param date birthdate threshold
     * @return list of users older than the given date
     */
    @GetMapping("/older/{date}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate date) {
        return userService.getUsersOlderThan(date);
    }

    /**
     * Updates an existing user with new data.
     *
     * @param id the user identifier
     * @param userDto updated user data
     * @return updated user as DTO
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the user identifier
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}