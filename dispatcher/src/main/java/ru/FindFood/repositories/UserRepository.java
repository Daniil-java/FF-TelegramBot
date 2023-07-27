package ru.FindFood.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.FindFood.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
