package gpi.scrum.dto;

import java.util.Date;
import java.util.List;

public class UserStoryDto {
    private Integer id;
    private String name;
    private String description;
    private Integer estimatedHours;
    private Integer realHours;
    private String state;
    private String priority;
    private Integer projectId;
    private Integer sprintId;


    public UserStoryDto(String name, String description, Integer estimatedHours, Integer realHours, String state, String priority, Integer projectId, Integer sprintId) {
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.realHours = realHours;
        this.state = state;
        this.priority = priority;
        this.projectId = projectId;
        this.sprintId = sprintId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getRealHours() {
        return realHours;
    }

    public void setRealHours(Integer realHours) {
        this.realHours = realHours;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }
}
