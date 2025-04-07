package gpi.scrum.service;

import gpi.scrum.domain.Project;
import gpi.scrum.domain.User;
import gpi.scrum.domain.UserStory;
import gpi.scrum.repository.ProjectRepository;
import gpi.scrum.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserStoryRepository userStoryRepository;

    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserStoryRepository userStoryRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userStoryRepository = userStoryRepository;
        this.userService = userService;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    public List<UserStory> getUserStoriesOfProject(Integer id) {
        return getProjectById(id).orElseThrow(() -> new RuntimeException("Project not found")).getProductBacklog();
    }

    public Project createProject(Project project, Integer userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        validateProjectDates(project);
        if (projectRepository.findByName(project.getName()).isPresent()) {
            throw new IllegalArgumentException("Project with the same name already exists");
        }
        if (project.getState() == null) {
            project.setState(Project.State.IN_PROGRESS);
        }
        Project newProject = new Project(project.getName(), project.getDescription(), project.getStartDate(),
                project.getEndDate(), project.getState());
        newProject.setProductOwner(user);
        return projectRepository.save(newProject);
    }

    public Project updateProject(Integer id, Project projectDetails, Integer userId) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (!project.getProductOwner().getId().equals(userId)) {
                throw new RuntimeException("User is not the product owner of the project");
            }
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            validateProjectDates(project);
            return projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    public void deleteProject(Integer id, Integer userId) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
        Project project = optionalProject.get();
        if (!project.getProductOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not the product owner of the project");
        }
        userStoryRepository.deleteByProjectId(project.getId());
        projectRepository.deleteById(id);
        } else {
        throw new RuntimeException("Project not found");
        }
    }

    public Project changeProjectState(Integer id, String newState, Integer userId) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (!project.getProductOwner().getId().equals(userId)) {
                throw new RuntimeException("User is not the product owner of the project");
            }
            project.setState(Project.State.valueOf(newState));
            return projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    private void validateProjectDates(Project project) {
        if (project.getStartDate().after(project.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public User getProjectProductOwner(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found")).getProductOwner();
    }

    public User getProjectScrumMaster(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found")).getScrumMaster();
    }

    public List<User> getProjectTeamMembers(Integer projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found")).getDevelopers();
    }

    public void addTeamMemberToProject(Integer projectId, Integer userId, Integer currentUserId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getProductOwner().getId().equals(currentUserId)) {
            throw new RuntimeException("User is not the product owner of the project");
        }
        if (project.getDevelopers().stream().anyMatch(user -> user.getId().equals(userId))) {
            throw new IllegalArgumentException("User is already a team member of the project");
        }
        if (project.getProductOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("User is the product owner of the project");
        }
        if (project.getScrumMaster() != null && project.getScrumMaster().getId().equals(userId)) {
            throw new IllegalArgumentException("User is the scrum master of the project");
        }
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.addDeveloper(user);
        projectRepository.save(project);
    }

    public void removeTeamMemberFromProject(Integer projectId, Integer userId, Integer currentUserId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getProductOwner().getId().equals(currentUserId)) {
            throw new RuntimeException("User is not the product owner of the project");
        }
        if (project.getDevelopers().stream().noneMatch(user -> user.getId().equals(userId))) {
            throw new IllegalArgumentException("User is not a team member of the project");
        }
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.removeDeveloper(user);
        projectRepository.save(project);
    }

    public void setProjectScrumMaster(Integer projectId, Integer userId, Integer currentUserId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getProductOwner().getId().equals(currentUserId)) {
            throw new RuntimeException("User is not the product owner of the project");
        }
        if (project.getScrumMaster() != null && project.getScrumMaster().getId().equals(userId)) {
            throw new IllegalArgumentException("User is already the scrum master of the project");
        }
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.setScrumMaster(user);
        projectRepository.save(project);
    }

}
