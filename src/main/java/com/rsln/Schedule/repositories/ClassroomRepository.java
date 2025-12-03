package com.rsln.Schedule.repositories;

import com.rsln.Schedule.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
