package gpi.scrum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)    
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "productOwner")
    @JsonManagedReference("productOwner")
    private List<Project> ownedProjects = new ArrayList<>();

    @OneToMany(mappedBy = "scrumMaster")
    @JsonManagedReference("scrumMaster")
    private List<Project> managedProjects = new ArrayList<>();

    @ManyToMany(mappedBy = "developers")
    @JsonIgnore
    private List<Project> developerProjects = new ArrayList<>();


    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<UserStory> userStories = new ArrayList<>();


    public User() {
    }

    public User(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getDeveloperProjects() {
        return developerProjects;
    }

    public void setDeveloperProjects(List<Project> developerProjects) {
        this.developerProjects = developerProjects;
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public void addOwnedProject(Project project) {
        this.ownedProjects.add(project);
    }

    public List<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(List<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addUserStory(UserStory userStory) {
        this.userStories.add(userStory);
    }

    public void removeUserStory(UserStory userStory) {
        this.userStories.remove(userStory);
    }
}