package com.recipe.data.auth.repository;

import com.recipe.data.auth.model.User;
import com.recipe.data.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);




}
