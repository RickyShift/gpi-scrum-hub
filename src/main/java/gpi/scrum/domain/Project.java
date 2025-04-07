package gpi.scrum.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    private Date startDate;

    private Date endDate;


    public enum State {
        IN_PROGRESS,
        COMPLETED
    }

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "product_owner_id")
    @JsonBackReference("productOwner")
    private User productOwner;

    @ManyToOne
    @JoinColumn(name = "scrum_master_id")
    @JsonBackReference("scrumMaster")
    private User scrumMaster;

    @ManyToMany
    @JoinTable(
            name = "project_developers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> developers = new ArrayList<>();


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference("project-backlog")
    private List<UserStory> productBacklog;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference("project-sprints")
    private List<Sprint> sprints;

    public Project() {
    }

    public Project(String name, String description, Date startDate, Date endDate, State state) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.productBacklog = new ArrayList<>();
        this.sprints = new ArrayList<>();
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<UserStory> getProductBacklog() {
        return productBacklog;
    }

    public void setProductBacklog(ArrayList<UserStory> productBacklog) {
        this.productBacklog = productBacklog;
    }

    public void addUserStory(UserStory userStory) {
        this.productBacklog.add(userStory);
    }

    public void removeUserStory(UserStory userStory) {
        this.productBacklog.remove(userStory);
    }

    public void addSprint(Sprint sprint) {
        this.sprints.add(sprint);
    }

    public void removeSprint(Sprint sprint) {
        this.sprints.remove(sprint);
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
        productOwner.addOwnedProject(this);
    }

    public User getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(User scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public List<User> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<User> developers) {
        this.developers = developers;
    }

    public void addDeveloper(User developer) {
        this.developers.add(developer);
    }

    public void removeDeveloper(User user) {
        this.developers.remove(user);
    }
}
