package gpi.scrum.service;

import gpi.scrum.domain.Project;
import gpi.scrum.domain.Sprint;
import gpi.scrum.dto.SprintDto;
import gpi.scrum.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SprintService {

    private final SprintRepository sprintRepository;

    private final ProjectService projectService;

    private final UserStoryService userStoryService;
    

    @Autowired
    public SprintService(SprintRepository sprintRepository, ProjectService projectService, UserStoryService userStoryService) {
        this.sprintRepository = sprintRepository;
        this.projectService = projectService;
        this.userStoryService = userStoryService;
    }

    public List<Sprint> getAllUserStories() {
        return sprintRepository.findAll();
    }

    public Optional<Sprint> getSprintById(Integer id) {
        return sprintRepository.findById(id);
    }

    public List<Sprint> getUserStoriesByProjectId(Integer projectId) {
        return sprintRepository.findByProjectId(projectId);
    }

    public Sprint createSprint(SprintDto newSprint) {
        Project project = projectService.getProjectById(newSprint.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
        Sprint sprint = new Sprint(newSprint.getName(), newSprint.getDescription(), newSprint.getStartDate(), newSprint.getEndDate(), Sprint.State.valueOf(newSprint.getState()),project);
        validateSprintDates(sprint);
        return sprintRepository.save(sprint);
    }

    public Sprint updateSprint(Integer id, Sprint sprintDetails) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(id);
        if (optionalSprint.isPresent()) {
            Sprint sprint = optionalSprint.get();
            sprint.setName(sprintDetails.getName());
            sprint.setDescription(sprintDetails.getDescription());
            sprint.setStartDate(sprintDetails.getStartDate());
            sprint.setEndDate(sprintDetails.getEndDate());
            validateSprintDates(sprint);
            return sprintRepository.save(sprint);
        } else {
            throw new RuntimeException("sprint not found");
        }
    }

    public void deleteSprint(Integer id) {
        sprintRepository.deleteById(id);
    }

    public Sprint changeSprintState(Integer id, String newState) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(id);
        if (optionalSprint.isPresent()) {
            Sprint sprint = optionalSprint.get();
            sprint.setState(Sprint.State.valueOf(newState));
            return sprintRepository.save(sprint);
        } else {
            throw new RuntimeException("Sprint not found");
        }
    }

    private void validateSprintDates(Sprint sprint) {
        if (sprint.getStartDate().after(sprint.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public void addUserStoryToSprint(Integer sprintId, Integer userStoryId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        if (optionalSprint.isPresent()) {
            Sprint sprint = optionalSprint.get();
            sprint.addUserStory(userStoryService.getUserStoryById(userStoryId).orElseThrow(() -> new RuntimeException("User story not found")));
            sprintRepository.save(sprint);
        } else {
            throw new RuntimeException("Sprint not found");
        }
    }

    public void removeUserStoryFromSprint(Integer sprintId, Integer userStoryId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        if (optionalSprint.isPresent()) {
            Sprint sprint = optionalSprint.get();
            sprint.removeUserStory(userStoryService.getUserStoryById(userStoryId
            ).orElseThrow(() -> new RuntimeException("User story not found")));
            sprintRepository.save(sprint);
        } else {
            throw new RuntimeException("Sprint not found");
        }
    }

    public Project getProjectBySprintId(Integer sprintId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        if (optionalSprint.isPresent()) {
            Sprint sprint = optionalSprint.get();
            return sprint.getProject();
        } else {
            throw new RuntimeException("Sprint not found");
        }
    }
}