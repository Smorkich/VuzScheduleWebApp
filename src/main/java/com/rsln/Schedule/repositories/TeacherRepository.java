package com.rsln.Schedule.repositories;

import com.rsln.Schedule.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
