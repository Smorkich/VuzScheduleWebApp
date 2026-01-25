package com.rsln.Schedule.lessonTests;

import com.rsln.Schedule.dtos.classroom.ClassroomResponseDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.dtos.lesson.LessonRequestDto;
import com.rsln.Schedule.dtos.lesson.LessonResponseDto;
import com.rsln.Schedule.dtos.subject.SubjectResponseDto;
import com.rsln.Schedule.dtos.teacher.TeacherResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.LessonMapper;
import com.rsln.Schedule.models.*;
import com.rsln.Schedule.repositories.*;
import com.rsln.Schedule.services.LessonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    LessonRepository lessonRepository;
    @Mock
    ClassroomRepository classroomRepository;
    @Mock
    GroupRepository groupRepository;
    @Mock
     SubjectRepository subjectRepository;
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    LessonMapper lessonMapper;

    @InjectMocks
    LessonService lessonService;

    @Test
    void getById_shouldReturnLesson_whenExists() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        LessonResponseDto dto = lessonResponseDto();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonResponseDto result = lessonService.getById(1L);

        assertThat(result).isNotNull();
    }
    @Test
    void getById_shouldThrow_whenNotFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lessonService.getById(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_shouldSaveLesson_andReturnDto() {
        LessonRequestDto request = new LessonRequestDto(
                1L, 2L, 3L, 4L,
                "Лекция",
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 30)
        );

        Lesson lesson = new Lesson();
        LessonResponseDto dto = lessonResponseDto();

        when(lessonMapper.toEntity(request)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonResponseDto result = lessonService.create(request);

        assertThat(result).isNotNull();
    }
    @Test
    void update_shouldUpdateLesson_whenAllEntitiesExist() {
        Lesson lesson = new Lesson();

        Group group = new Group();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        Classroom classroom = new Classroom();

        LessonRequestDto dto = new LessonRequestDto(
                1L, 2L, 3L, 4L,
                "Лекция",
                DayOfWeek.TUESDAY,
                LocalTime.of(9, 0),
                LocalTime.of(10, 30)
        );

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));
        when(teacherRepository.findById(3L)).thenReturn(Optional.of(teacher));
        when(classroomRepository.findById(4L)).thenReturn(Optional.of(classroom));

        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson))
                .thenReturn(lessonResponseDto());


        LessonResponseDto result = lessonService.update(1L, dto);

        verify(lessonMapper).updateEntity(
                lesson, dto, group, subject, teacher, classroom
        );

        assertThat(result).isNotNull();
    }
    @Test
    void delete_shouldCallRepository() {
        lessonService.delete(1L);

        verify(lessonRepository).deleteById(1L);
    }
    @Test
    void getByGroupAndDay_shouldReturnLessons() {
        Lesson lesson = new Lesson();

        when(lessonRepository.findByGroupIdAndDayOfWeek(1L, DayOfWeek.MONDAY))
                .thenReturn(List.of(lesson));

        when(lessonMapper.toDto(lesson))
                .thenReturn(lessonResponseDto());

        List<LessonResponseDto> result =
                lessonService.getByGroupAndDay(1L, DayOfWeek.MONDAY);

        assertThat(result).hasSize(1);
    }


    private GroupResponseDto groupDto() {
        return new GroupResponseDto(1L, "IV-1", 3);
    }

    private SubjectResponseDto subjectDto() {
        return new SubjectResponseDto(1L, "Math");
    }

    private TeacherResponseDto teacherDto() {
        return new TeacherResponseDto(1L, "Ivanov I.I.", "Math");
    }

    private ClassroomResponseDto classroomDto() {
        return new ClassroomResponseDto(1L, "101A");
    }
    private LessonResponseDto lessonResponseDto() {
        return new LessonResponseDto(
                1L,
                groupDto(),
                subjectDto(),
                teacherDto(),
                classroomDto(),
                "LECTURE",
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 30)
        );
    }

}



