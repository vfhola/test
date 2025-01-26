package org.example.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestDataModel, Long> {
}
