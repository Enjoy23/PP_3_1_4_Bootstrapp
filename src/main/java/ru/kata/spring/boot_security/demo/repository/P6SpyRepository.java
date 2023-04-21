package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.P6Spy;

public interface P6SpyRepository extends JpaRepository<P6Spy,Long> {
}
