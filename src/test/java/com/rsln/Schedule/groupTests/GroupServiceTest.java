package com.rsln.Schedule.groupTests;

import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.exceptions.NotFoundException;
import com.rsln.Schedule.mappers.GroupMapper;
import com.rsln.Schedule.models.Group;
import com.rsln.Schedule.repositories.GroupRepository;
import com.rsln.Schedule.services.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;
    @Test
    void getAll_shouldReturnListOfGroupResponseDto() {

        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("A-101");

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("B-202");

        GroupResponseDto dto1 = new GroupResponseDto(1L, "A-101", 1);
        GroupResponseDto dto2 = new GroupResponseDto(2L, "B-202", 2);

        when(groupRepository.findAll()).thenReturn(List.of(group1, group2));
        when(groupMapper.toDto(group1)).thenReturn(dto1);
        when(groupMapper.toDto(group2)).thenReturn(dto2);


        List<GroupResponseDto> result = groupService.getAll();


        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(groupRepository).findAll();
    }

    @Test
    void getById_shouldReturnGroup_whenExists() {

        Long id = 1L;

        Group group = new Group();
        group.setId(id);
        group.setName("A-101");

        GroupResponseDto dto = new GroupResponseDto(id, "A-101", 1);

        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(groupMapper.toDto(group)).thenReturn(dto);


        GroupResponseDto result = groupService.getById(id);


        assertEquals(dto, result);
        verify(groupRepository).findById(id);
    }
    @Test
    void getById_shouldThrowNotFoundException_whenGroupNotFound() {

        Long id = 999L;
        when(groupRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> groupService.getById(id));

        verify(groupRepository).findById(id);
    }

    @Test
    void create_shouldSaveGroup_andReturnDto() {

        GroupRequestDto request = new GroupRequestDto("A-101", 1);

        Group entity = new Group();
        entity.setName("A-101");

        Group savedEntity = new Group();
        savedEntity.setId(1L);
        savedEntity.setName("A-101");

        GroupResponseDto response =
                new GroupResponseDto(1L, "A-101", 1);

        when(groupMapper.toEntity(request)).thenReturn(entity);
        when(groupRepository.save(entity)).thenReturn(savedEntity);
        when(groupMapper.toDto(savedEntity)).thenReturn(response);


        GroupResponseDto result = groupService.create(request);


        assertEquals(response, result);
        verify(groupRepository).save(entity);
    }
    @Test
    void update_shouldUpdateGroup_andReturnDto() {

        Long id = 1L;
        GroupRequestDto updated =
                new GroupRequestDto("NEW_NAME", 2);

        Group existing = new Group();
        existing.setId(id);
        existing.setName("OLD_NAME");

        GroupResponseDto response =
                new GroupResponseDto(id, "NEW_NAME", 2);

        when(groupRepository.findById(id)).thenReturn(Optional.of(existing));
        when(groupRepository.save(existing)).thenReturn(existing);
        when(groupMapper.toDto(existing)).thenReturn(response);


        GroupResponseDto result = groupService.update(id, updated);


        assertEquals("NEW_NAME", existing.getName());
        assertEquals(response, result);

        verify(groupRepository).save(existing);
    }
    @Test
    void delete_shouldCallRepositoryDeleteById() {

        Long id = 1L;

        groupService.delete(id);

        verify(groupRepository).deleteById(id);
    }

}

