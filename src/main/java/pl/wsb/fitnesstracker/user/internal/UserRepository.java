package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }
    /**
     * Finds users whose email contains the given fragment (case-insensitive).
     * This method loads all users and filters them in memory using a stream.
     *
     * @param fragment email fragment to search for
     * @return list of users whose email contains the given fragment (case-insensitive)
     */
    default java.util.List<User> findByEmailContainingIgnoreCase(String fragment) {
        return findAll().stream()
                .filter(user -> user.getEmail() != null)
                .filter(user -> user.getEmail().toLowerCase()
                        .contains(fragment.toLowerCase()))
                .toList();
    }

}
