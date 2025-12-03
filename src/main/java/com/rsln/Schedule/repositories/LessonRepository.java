package com.rsln.Schedule.repositories;

import com.rsln.Schedule.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<List<Lesson>> findByGroupId(Long groupId);

    Optional<List<Lesson>> findByTeacherId(Long teacherId);

    Optional<List<Lesson>> findByDayOfWeek(DayOfWeek dayOfWeek);

    Optional<List<Lesson>> findByGroupIdAndDayOfWeek(Long groupId, DayOfWeek dayOfWeek);
}
