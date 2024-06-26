package org.ofektom.airwaysdemobackend.repository;


import org.ofektom.airwaysdemobackend.enums.Role;
import org.ofektom.airwaysdemobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findUserByUserRole(Role role);

    User findUserByEmail(String email);
}
