package com.example.Linkedup.repository;

import com.example.Linkedup.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUserId(UUID userId);
}


//Creating interface cause spring automatically generate implementation
//extends JpaRepository<Profile, String>:- gives you all CRUD operations automatically.
// <Profile String>:- Profile The Entity (your table model)  String Type of Primary Key
