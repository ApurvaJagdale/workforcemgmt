package com.test.workforcemgmt.mapper;

import com.test.workforcemgmt.dto.TaskDto;
import com.test.workforcemgmt.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto toDto(Task task);

    //You can also add reverse mapping if needed
    Task toEntity(TaskDto dto);
}
