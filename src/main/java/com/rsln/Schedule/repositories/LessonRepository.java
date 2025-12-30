package com.rsln.Schedule.repositories;

import com.rsln.Schedule.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByGroupId(Long groupId);

    List<Lesson> findByTeacherId(Long teacherId);

    List<Lesson> findByTeacherIdAndDayOfWeek(Long teacherId, DayOfWeek day);

    List<Lesson> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<Lesson> findByGroupIdAndDayOfWeek(Long groupId, DayOfWeek dayOfWeek);
}
