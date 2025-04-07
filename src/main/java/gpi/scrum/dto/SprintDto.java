package gpi.scrum.dto;

import java.util.Date;
import java.util.List;

public class SprintDto {
    private Integer id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String state;
    private Integer projectId;
    private List<Integer> userStoryIds;

    public SprintDto(String name, String description, Date startDate, Date endDate, Integer projectId, String state,List<Integer> userStoryIds) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.userStoryIds = userStoryIds;
        this.state = state;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getUserStoryIds() {
        return userStoryIds;
    }

    public void setUserStoryIds(List<Integer> userStoryIds) {
        this.userStoryIds = userStoryIds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}