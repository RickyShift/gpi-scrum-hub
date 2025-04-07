package gpi.scrum.controller;

import gpi.scrum.domain.User;
import gpi.scrum.service.ProjectService;
import gpi.scrum.domain.Project;
import gpi.scrum.domain.UserStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/userStories")
    public ResponseEntity<List<UserStory>> getUserStoriesOfProject(@PathVariable Integer id) {
        List<UserStory> userStories = projectService.getUserStoriesOfProject(id);
        return ResponseEntity.ok(userStories);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Project> createProject(@PathVariable Integer userId ,@RequestBody Project project) {
        Project createdProject = projectService.createProject(project, userId);
        return ResponseEntity.ok(createdProject);
    }

    @PutMapping("/update/{id}/{userId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @PathVariable Integer userId , @RequestBody Project projectDetails) {
        try {
            Project updatedProject = projectService.updateProject(id, projectDetails, userId);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id, @PathVariable Integer userId) {
        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/changeState/{id}/{userId}")
    public ResponseEntity<Project> changeProjectState(@PathVariable Integer id, @RequestParam String newState, @PathVariable Integer userId) {
        try {
            Project updatedProject = projectService.changeProjectState(id, newState, userId);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/productOwner")
    public ResponseEntity<User> getProjectProductOwner(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectProductOwner(id));
    }

    @GetMapping("/{id}/scrumMaster")
    public ResponseEntity<User> getProjectScrumMaster(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectScrumMaster(id));
    }

    @GetMapping("/{id}/teamMembers")
    public ResponseEntity<List<User>> getProjectTeamMembers(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectTeamMembers(id));
    }

    @PostMapping("/{projectId}/addMember/{userId}/{currentUserId}")
    public ResponseEntity<Void> addTeamMemberToProject(@PathVariable Integer projectId, @PathVariable Integer userId, @PathVariable Integer currentUserId) {
        projectService.addTeamMemberToProject(projectId, userId, currentUserId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/removeMember/{userId}/{currentUserId}")
    public ResponseEntity<Void> removeTeamMemberFromProject(@PathVariable Integer projectId, @PathVariable Integer userId, @PathVariable Integer currentUserId) {
        projectService.removeTeamMemberFromProject(projectId, userId, currentUserId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{projectId}/setScrumMaster/{userId}/{currentUserId}")
    public ResponseEntity<Void> setProjectScrumMaster(@PathVariable Integer projectId, @PathVariable Integer userId, @PathVariable Integer currentUserId) {
        projectService.setProjectScrumMaster(projectId, userId, currentUserId);
        return ResponseEntity.ok().build();
    }
}
