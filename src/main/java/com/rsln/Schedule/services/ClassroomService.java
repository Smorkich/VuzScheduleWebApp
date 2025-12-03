package com.rsln.Schedule.services;

import com.rsln.Schedule.models.Classroom;
import com.rsln.Schedule.repositories.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAll() {
        return classroomRepository.findAll();
    }

    public Classroom getById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
    }

    public Classroom create(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public Classroom update(Long id, Classroom updated) {
        Classroom classroom = getById(id);
        classroom.setName(updated.getName());
        return classroomRepository.save(classroom);
    }

    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }
}
