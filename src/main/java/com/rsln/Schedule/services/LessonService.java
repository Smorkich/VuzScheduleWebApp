package com.rsln.Schedule.services;

import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.ClassroomMapper;
import com.rsln.Schedule.mappers.LessonMapper;
import com.rsln.Schedule.models.*;
import com.rsln.Schedule.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//TODO на все сервисы мб интерфейсы прикрутить
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ClassroomRepository classroomRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final LessonMapper lessonMapper;
    private static final Logger log =
            LoggerFactory.getLogger(LessonService.class);

    public LessonService(LessonRepository lessonRepository, ClassroomRepository classroomRepository, GroupRepository groupRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.classroomRepository = classroomRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.lessonMapper = lessonMapper;
    }

    public List<LessonResponseDto> getAll() {
        log.info("get all Lessons");
        return lessonRepository.findAll().stream().map(lessonMapper::toDto).toList();
    }

    public LessonResponseDto getById(Long id) {
        log.info("get Lesson by id: {}", id);
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Занятие не найдено"));
        return lessonMapper.toDto(lesson);
    }

    public LessonResponseDto create(LessonRequestDto dto) {
        log.info(
                "Создание занятия: groupId={}, teacherId={}, day={}, time={}–{}",
                dto.groupId(),
                dto.teacherId(),
                dto.dayOfWeek(),
                dto.startTime(),
                dto.endTime()
        );
        Lesson lesson = lessonMapper.toEntity(dto);
        lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }


    public LessonResponseDto update(Long id, LessonRequestDto dto) {
        log.info(
                "update lesson: groupId={}, teacherId={}, day={}, time={}–{}",
                dto.groupId(),
                dto.teacherId(),
                dto.dayOfWeek(),
                dto.startTime(),
                dto.endTime()
        );
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Занятие не найдено"));

        Group group = groupRepository.findById(dto.groupId())
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));

        Subject subject = subjectRepository.findById(dto.subjectId())
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new NotFoundException("Преподаватель не найден"));

        Classroom classroom = classroomRepository.findById(dto.classroomId())
                .orElseThrow(() -> new NotFoundException("Аудитория не найдена"));

        lessonMapper.updateEntity(
                lesson,
                dto,
                group,
                subject,
                teacher,
                classroom
        );

        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    public void delete(Long id) {
        log.info("delete lesson by id: {}", id);
        lessonRepository.deleteById(id);
    }

    public List<LessonResponseDto> getByGroupAndDay(Long groupId, DayOfWeek day) {
        log.info("get lesson by groupId and day: groupId={}, day={}", groupId, day);
        List<Lesson> lessons = (day == null)
                ? lessonRepository.findByGroupId(groupId)
                : lessonRepository.findByGroupIdAndDayOfWeek(groupId, day);

        return lessons.stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    public List<LessonResponseDto> getScheduleForTeacher(
            Long teacherId,
            DayOfWeek day
    ) {
        log.info("get schedule for teacherId={}, day={}", teacherId, day);

        List<Lesson> lessons = (day == null)
                ? lessonRepository.findByTeacherId(teacherId)
                : lessonRepository.findByTeacherIdAndDayOfWeek(teacherId, day);

        return lessons.stream()
                .map(lessonMapper::toDto)
                .toList();
    }
    public List<LessonResponseDto> getByGroupAndDate(Long groupId, LocalDate date) {
        log.info("Запрос расписания: groupId={}, date={}", groupId, date);
        return lessonRepository.findByGroupIdAndLessonDate(groupId, date)
                .stream().map(lessonMapper::toDto).toList();
    }

}
