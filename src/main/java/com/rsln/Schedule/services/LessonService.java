package com.rsln.Schedule.services;

import com.rsln.Schedule.models.Lesson;
import com.rsln.Schedule.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

//TODO на все сервисы мб интерфейсы прикрутить
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson update(Long id, Lesson updated) {
        Lesson lesson = getById(id);

        lesson.setGroup(updated.getGroup());
        lesson.setTeacher(updated.getTeacher());
        lesson.setSubject(updated.getSubject());
        lesson.setClassroom(updated.getClassroom());
        lesson.setLessonType(updated.getLessonType());
        lesson.setDayOfWeek(updated.getDayOfWeek());
        lesson.setStartTime(updated.getStartTime());
        lesson.setEndTime(updated.getEndTime());

        return lessonRepository.save(lesson);
    }

    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    public List<Lesson> getByGroupAndDay(Long groupId, DayOfWeek day) {
        return lessonRepository.findByGroupIdAndDayOfWeek(groupId, day).orElseThrow(()
                -> new RuntimeException("Lesson not found"));
    }
}
