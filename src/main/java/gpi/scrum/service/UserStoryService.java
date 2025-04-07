package gpi.scrum.service;

import gpi.scrum.domain.Project;
import gpi.scrum.domain.Sprint;
import gpi.scrum.domain.User;
import gpi.scrum.domain.UserStory;
import gpi.scrum.dto.UserStoryDto;
import gpi.scrum.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository, ProjectService projectService, UserService userService) {
        this.userStoryRepository = userStoryRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    public Optional<UserStory> getUserStoryById(Integer id) {
        return userStoryRepository.findById(id);
    }

    public List<UserStory> getUserStoriesByProjectId(Integer projectId) {
        return userStoryRepository.findByProjectId(projectId);
    }

    public UserStory createUserStory(UserStoryDto userStoryDto, Integer userId) {
        Project project = projectService.getProjectById(userStoryDto.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getProductOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not the product owner of the project");
        }
        UserStory userStory = new UserStory(userStoryDto.getName(), userStoryDto.getDescription(), userStoryDto.getEstimatedHours(), userStoryDto.getRealHours(), UserStory.State.valueOf(userStoryDto.getState()), UserStory.Priority.valueOf(userStoryDto.getPriority()), project);
        return userStoryRepository.save(userStory);
    }

    public UserStory updateUserStory(Integer id, UserStory userStoryDetails) {
        Optional<UserStory> optionalUserStory = userStoryRepository.findById(id);
        if (optionalUserStory.isPresent()) {
            UserStory userStory = optionalUserStory.get();
            userStory.setName(userStoryDetails.getName());
            userStory.setDescription(userStoryDetails.getDescription());
            userStory.setEstimatedHours(userStoryDetails.getEstimatedHours());
            userStory.setRealHours(userStoryDetails.getRealHours());
            userStory.setPriority(userStoryDetails.getPriority());
            return userStoryRepository.save(userStory);
        } else {
            throw new RuntimeException("user story not found");
        }
    }

    public void deleteUserStory(Integer id) {
        userStoryRepository.deleteById(id);
    }

    public Project getProject(Integer id) {
        return getUserStoryById(id).get().getProject();
    }

    public Sprint getSprint(Integer id) {
        return getUserStoryById(id).get().getSprint();
    }

    public UserStory changeUserStoryState(Integer id, String newState) {
        Optional<UserStory> optionalUserStory = userStoryRepository.findById(id);
        if (optionalUserStory.isPresent()) {
            UserStory userStory = optionalUserStory.get();
            userStory.setState(UserStory.State.valueOf(newState));
            if (userStory.getState() == UserStory.State.COMPLETED) {
                userStory.setCompletionDate(new java.util.Date());
            }
            return userStoryRepository.save(userStory);
        } else {
            throw new RuntimeException("UserStory not found");
        }
    }

    public void assignUserStoryToUser(Integer userStoryId, Integer userId) {
        Optional<UserStory> optionalUserStory = userStoryRepository.findById(userStoryId);
        if (optionalUserStory.isPresent()) {
            UserStory userStory = optionalUserStory.get();
            userStory.addUser(userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            userStoryRepository.save(userStory);
        } else {
            throw new RuntimeException("UserStory not found");
        }
    }

    public void removeUserFromUserStory(Integer userStoryId, Integer userId) {
        Optional<UserStory> optionalUserStory = userStoryRepository.findById(userStoryId);
        if (optionalUserStory.isPresent()) {
            UserStory userStory = optionalUserStory.get();
            userStory.removeUser(userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            userStoryRepository.save(userStory);
        } else {
            throw new RuntimeException("UserStory not found");
        }
    }

    public List<User> getUsersByUserStoryId(Integer userStoryId) {
        return getUserStoryById(userStoryId).get().getUsers();
    }
}