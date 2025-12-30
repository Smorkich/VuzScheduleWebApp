package com.rsln.Schedule.groupTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsln.Schedule.ScheduleApplication;
import com.rsln.Schedule.controllers.GroupController;
import com.rsln.Schedule.dtos.group.GroupRequestDto;
import com.rsln.Schedule.dtos.group.GroupResponseDto;
import com.rsln.Schedule.services.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GroupService groupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<GroupResponseDto> response = List.of(
                new GroupResponseDto(1L, "A-101", 1),
                new GroupResponseDto(2L, "B-202", 2)
        );

        when(groupService.getAll()).thenReturn(response);

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("A-101"));
    }

    @Test
    void getById_shouldReturnGroup() throws Exception {
        GroupResponseDto response =
                new GroupResponseDto(1L, "A-101", 1);

        when(groupService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/groups/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("A-101"));
    }
    @Test
    void create_shouldReturnCreatedGroup() throws Exception {
        GroupRequestDto request = new GroupRequestDto("A-101", 1);
        GroupResponseDto response = new GroupResponseDto(1L, "A-101", 1);

        when(groupService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("A-101"));
    }
    @Test
    void update_shouldReturnUpdatedGroup() throws Exception {
        GroupRequestDto request = new GroupRequestDto("B-202", 2);
        GroupResponseDto response = new GroupResponseDto(1L, "B-202", 2);

        when(groupService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/groups/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("B-202"));
    }
    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(groupService).delete(1L);

        mockMvc.perform(delete("/api/groups/{id}", 1))
                .andExpect(status().isNoContent());

        verify(groupService).delete(1L);
    }
}
