package com.rsln.Schedule.mappers;

import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.models.*;
import com.rsln.Schedule.repositories.ClassroomRepository;
import com.rsln.Schedule.repositories.GroupRepository;
import com.rsln.Schedule.repositories.SubjectRepository;
import com.rsln.Schedule.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;

    public LessonMapper(GroupRepository groupRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, ClassroomRepository classroomRepository) {
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
    }

    public Lesson toEntity(LessonRequestDto dto) {
        Lesson lesson = new Lesson();

        lesson.setGroup(groupRepository.findById(dto.groupId()).orElseThrow());
        lesson.setSubject(subjectRepository.findById(dto.subjectId()).orElseThrow());
        lesson.setTeacher(teacherRepository.findById(dto.teacherId()).orElseThrow());
        lesson.setClassroom(classroomRepository.findById(dto.classroomId()).orElseThrow());

        lesson.setLessonDate(dto.lessonDate());
        lesson.setLessonType(dto.lessonType());
        lesson.setDayOfWeek(dto.dayOfWeek());
        lesson.setStartTime(dto.startTime());
        lesson.setEndTime(dto.endTime());

        return lesson;
    }

    public void updateEntity(Lesson lesson,
                             LessonRequestDto dto,
                             Group group,
                             Subject subject,
                             Teacher teacher,
                             Classroom classroom) {

        lesson.setGroup(group);
        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setClassroom(classroom);
        lesson.setLessonType(dto.lessonType());
        lesson.setLessonDate(dto.lessonDate());
        lesson.setDayOfWeek(dto.dayOfWeek());
        lesson.setStartTime(dto.startTime());
        lesson.setEndTime(dto.endTime());
    }

    public LessonResponseDto toDto(Lesson entity) {
        return new LessonResponseDto(
                entity.getId(),
                new GroupResponseDto(entity.getGroup().getId(), entity.getGroup().getName(), entity.getGroup().getCourse()),
                new SubjectResponseDto(entity.getSubject().getId(), entity.getSubject().getName()),
                new TeacherResponseDto(entity.getTeacher().getId(), entity.getTeacher().getFullName(), entity.getTeacher().getDepartment()),
                new ClassroomResponseDto(entity.getClassroom().getId(), entity.getClassroom().getName()),
                entity.getLessonType(),
                entity.getLessonDate(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}

