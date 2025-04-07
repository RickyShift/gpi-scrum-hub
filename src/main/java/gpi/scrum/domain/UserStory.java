package gpi.scrum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private Integer estimatedHours;

    private Integer realHours;


    @ManyToOne()
    @JsonBackReference("project-backlog")
    private Project project;

    @ManyToOne()
    @JsonBackReference("sprint-backlog")
    private Sprint sprint;

    @ManyToMany()
    @JoinTable(
            name = "userstory_users",
            joinColumns = @JoinColumn(name = "userstory_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> users ;

    public enum State {
        TO_DO,
        IN_PROGRESS,
        COMPLETED
    }

    @Enumerated(EnumType.STRING)
    private State state;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private Date completionDate;

    public UserStory() {

    }

    public UserStory(String name, String description, Integer estimatedHours, Integer realHours, State state, Priority priority, Project project) {
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.realHours = realHours;
        this.project = project;
        this.state = state;
        this.priority = priority;
    }

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.addUserStory(this);
    }

    public void removeUser(User user) {
        user.removeUserStory(this);
        this.users.remove(user);
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

}