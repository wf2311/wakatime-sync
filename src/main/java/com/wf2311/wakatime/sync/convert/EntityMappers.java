package com.wf2311.wakatime.sync.convert;

import com.wf2311.wakatime.sync.domain.Duration;
import com.wf2311.wakatime.sync.domain.ProjectDuration;
import com.wf2311.wakatime.sync.domain.day.*;
import com.wf2311.wakatime.sync.entity.*;
import com.wf2311.wakatime.sync.spider.JsonParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/6/16 16:13.
 */
@Mapper(imports = {JsonParser.class, EntityConvertHelper.class})
public interface EntityMappers {
    EntityMappers INSTANCE = Mappers.getMapper(EntityMappers.class);


    @Mappings({
//            @Mapping(target = "dependencies", expression = "java(JsonParser.parseDependencies(duration.getDependencies()))"),
            @Mapping(target = "startTime", expression = "java(EntityConvertHelper.doubleToDate(duration.getSecond()))"),
            @Mapping(target = "endTime", expression = "java(EntityConvertHelper.doubleToDate(duration.getSecond()+duration.getDuration()))"),
            @Mapping(target = "createdTime", source = "createdTime"),
    })
    DurationEntity convert(Duration duration, LocalDateTime createdTime);


    @Mappings({
//            @Mapping(target = "dependencies", expression = "java(JsonParser.parseDependencies(duration.getDependencies()))"),
            @Mapping(target = "startTime", expression = "java(EntityConvertHelper.doubleToDate(duration.getSecond()))"),
            @Mapping(target = "endTime", expression = "java(EntityConvertHelper.doubleToDate(duration.getSecond()+duration.getDuration()))"),
            @Mapping(target = "createdTime", source = "createdTime"),
    })
    ProjectDurationEntity convert(ProjectDuration duration, LocalDateTime createdTime);

    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayCategoryEntity convert(DayCategory category, LocalDate day, LocalDateTime createdTime);

    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayDependencyEntity convert(DayDependency dayDependency, LocalDate day, LocalDateTime createdTime);

    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayEditorEntity convert(DayEditor editor, LocalDate day, LocalDateTime createdTime);

    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayGrandTotalEntity convert(DayGrandTotal total, LocalDate day, LocalDateTime createdTime);



    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayLanguageEntity convert(DayLanguage language, LocalDate day, LocalDateTime createdTime);



    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayOperateSystemEntity convert(DayOperateSystem system, LocalDate day, LocalDateTime createdTime);


    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime")
    })
    DayProjectEntity convert(DayProject project, LocalDate day, LocalDateTime createdTime);


    @Mappings({
            @Mapping(target = "day", source = "day"),
            @Mapping(target = "createdTime", source = "createdTime"),
            @Mapping(target = "simpleName", expression = "java(EntityConvertHelper.getSimpleName(entity.getName()))"),
    })
    DayEntityEntity convert(DayEntity entity, LocalDate day, LocalDateTime createdTime);

}
