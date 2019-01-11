package com.wf2311.wakatime.sync.convert;

import com.wf2311.wakatime.sync.domain.DaySummary;
import com.wf2311.wakatime.sync.entity.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:44.
 */
public class DaySummaryConverter {
    private DaySummary summary;
    private LocalDate day;
    private LocalDateTime now;
    private List<DayEditorEntity> editors = new ArrayList<>();
    private List<DayEntityEntity> entities = new ArrayList<>();
    private List<DayCategoryEntity> categories = new ArrayList<>();
    private List<DayDependencyEntity> dependencies = new ArrayList<>();
    private List<DayLanguageEntity> languages = new ArrayList<>();
    private List<DayOperateSystemEntity> operateSystems = new ArrayList<>();
    private List<DayProjectEntity> projects = new ArrayList<>();
    private DayGrandTotalEntity grandTotal = new DayGrandTotalEntity();

    public static DaySummaryConverter of(DaySummary summary) {
        return new DaySummaryConverter(summary);
    }

    private DaySummaryConverter(DaySummary summary) {
        this.summary = summary;
        this.day = summary.getDate();
        this.now = LocalDateTime.now();
        convert();
    }

    private void convert() {
        if (!CollectionUtils.isEmpty(summary.getCategories())) {
            this.categories = summary.getCategories().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(summary.getDependencies())) {
            this.dependencies = summary.getDependencies().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(summary.getEditors())) {
            this.editors = summary.getEditors().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(summary.getEntities())) {
            this.entities = summary.getEntities().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (summary.getGrandTotal() != null) {
            this.grandTotal = summary.getGrandTotal().convert(day, now);
        }
        if (!CollectionUtils.isEmpty(summary.getLanguages())) {
            this.languages = summary.getLanguages().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(summary.getSystem())) {
            this.operateSystems = summary.getSystem().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(summary.getProjects())) {
            this.projects = summary.getProjects().stream().map(s -> s.convert(day, now)).collect(Collectors.toList());
        }
    }


    public DaySummary getSummary() {
        return summary;
    }

    public LocalDate getDay() {
        return day;
    }

    public List<DayEditorEntity> getEditors() {
        return editors;
    }

    public List<DayEntityEntity> getEntities() {
        return entities;
    }

    public List<DayCategoryEntity> getCategories() {
        return categories;
    }

    public List<DayDependencyEntity> getDependencies() {
        return dependencies;
    }

    public List<DayLanguageEntity> getLanguages() {
        return languages;
    }

    public List<DayOperateSystemEntity> getOperateSystems() {
        return operateSystems;
    }

    public List<DayProjectEntity> getProjects() {
        return projects;
    }

    public DayGrandTotalEntity getGrandTotal() {
        return grandTotal;
    }
}
