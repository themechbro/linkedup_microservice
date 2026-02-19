package com.example.Linkedup.repository;

import com.example.Linkedup.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUserId(UUID userId);


    @Query(
        """
               SELECT p FROM Profile p WHERE p.userId= :userId 
                """
    )
    List <Profile> userDetails(@Param("userId") UUID userId);
}


//Creating interface cause spring automatically generate implementation
//extends JpaRepository<Profile, String>:- gives you all CRUD operations automatically.
// <Profile String>:- Profile The Entity (your table model)  String Type of Primary Key
