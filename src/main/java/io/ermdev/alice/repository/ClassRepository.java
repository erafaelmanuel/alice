package io.ermdev.alice.repository;

import io.ermdev.alice.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
