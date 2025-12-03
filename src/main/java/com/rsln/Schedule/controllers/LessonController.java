package com.rsln.Schedule.controllers;

import com.rsln.Schedule.models.Lesson;
import com.rsln.Schedule.services.LessonService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAll() {
        return lessonService.getAll();
    }

    @GetMapping("/{id}")
    public Lesson getById(@PathVariable Long id) {
        return lessonService.getById(id);
    }

    @PostMapping
    public Lesson create(@RequestBody Lesson lesson) {
        return lessonService.create(lesson);
    }

    @PutMapping("/{id}")
    public Lesson update(@PathVariable Long id, @RequestBody Lesson updated) {
        return lessonService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.delete(id);
    }

    @GetMapping("/by-group/{groupId}")
    public List<Lesson> getByGroup(@PathVariable Long groupId) {
        return lessonService.getByGroupAndDay(groupId, null);
    }

    @GetMapping("/schedule")
    public List<Lesson> getSchedule(
            @RequestParam Long groupId,
            @RequestParam String day
    ) {
        return lessonService.getByGroupAndDay(groupId, DayOfWeek.valueOf(day.toUpperCase()));
    }
}
