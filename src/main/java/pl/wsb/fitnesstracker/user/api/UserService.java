package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user The user to be created
     * @return The created user
     */
    User createUser(User user);

    /**
     * Retrieves all users in the system.
     *
     * @return list of users as DTOs
     */
    List<UserDto> getUsers();

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user identifier
     * @return user data as DTO
     */
    UserDto getUserById(Long id);

    /**
     * Searches for users by email address.
     * The search is not case-insensitive and may match partial email fragments.
     *
     * @param email email fragment used for searching
     * @return list of users matching the search criteria
     */
    List<UserDto> getUserByEmail(String email);

    /**
     * Retrieves users older than the specified date.
     *
     * @param date the birthdate threshold used for filtering users
     * @return list of users older than the given date
     */
    List<UserDto> getUsersOlderThan(LocalDate date);

    /**
     * Updates an existing user with new data.
     *
     * @param id the user identifier
     * @param userDto the updated user data
     * @return updated user as DTO
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * Deletes a user from the system.
     *
     * @param id the user identifier
     */
    void deleteUser(Long id);

}
