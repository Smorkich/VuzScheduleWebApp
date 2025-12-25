-- ======== SUBJECTS ========
INSERT INTO subjects (id, name)
VALUES (1, 'Mathematics'),
       (2, 'Programming'),
       (3, 'Physics');

-- ======== GROUPS (student_group) ========
INSERT INTO student_group (id, name, course)
VALUES (1, 'CS-101', 1),
       (2, 'CS-102', 1),
       (3, 'MATH-201', 2);

-- ======== TEACHERS ========
INSERT INTO teachers (id, full_name, department)
VALUES (1, 'Ivan Petrov', 'Computer Science'),
       (2, 'Anna Sidorova', 'Mathematics'),
       (3, 'Dmitry Orlov', 'Physics');

-- ======== CLASSROOMS ========
INSERT INTO classrooms (id, name)
VALUES (1, 'Room 101'),
       (2, 'Room 202'),
       (3, 'Lab 1');

-- ======== LESSONS ========
INSERT INTO lessons (id, group_id, subject_id, teacher_id, classroom_id,
                     lesson_type, day_of_week, start_time, end_time)
VALUES
    (1, 1, 2, 1, 1, 'Lecture',  'MONDAY',   '09:00', '10:30'),
    (2, 1, 2, 1, 3, 'Practice', 'WEDNESDAY','11:00', '12:30'),
    (3, 2, 1, 2, 2, 'Lecture',  'TUESDAY',  '10:00', '11:30'),
    (4, 3, 3, 3, 1, 'Lab',      'THURSDAY', '14:00', '16:00');
