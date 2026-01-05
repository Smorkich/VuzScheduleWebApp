package com.rsln.Schedule.repositories;

import com.rsln.Schedule.models.Lesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByGroupId(Long groupId);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByTeacherId(Long teacherId);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByGroupIdAndLessonDate(Long groupId, LocalDate lessonDate);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByTeacherIdAndLessonDate(Long teacherId, LocalDate lessonDate);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByTeacherIdAndDayOfWeek(Long teacherId, DayOfWeek day);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByDayOfWeek(DayOfWeek dayOfWeek);

    @EntityGraph(attributePaths = {"group", "subject", "teacher", "classroom"})
    List<Lesson> findByGroupIdAndDayOfWeek(Long groupId, DayOfWeek dayOfWeek);
}
