package com.rsln.Schedule.controllers;

import com.rsln.Schedule.models.Classroom;
import com.rsln.Schedule.services.ClassroomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<Classroom> getAll() {
        return classroomService.getAll();
    }

    @GetMapping("/{id}")
    public Classroom getById(@PathVariable Long id) {
        return classroomService.getById(id);
    }

    @PostMapping
    public Classroom create(@RequestBody Classroom classroom) {
        return classroomService.create(classroom);
    }

    @PutMapping("/{id}")
    public Classroom update(@PathVariable Long id, @RequestBody Classroom updated) {
        return classroomService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classroomService.delete(id);
    }
}
