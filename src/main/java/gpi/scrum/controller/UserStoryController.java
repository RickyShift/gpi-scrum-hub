package gpi.scrum.controller;

import gpi.scrum.domain.Sprint;
import gpi.scrum.domain.User;
import gpi.scrum.service.UserStoryService;
import gpi.scrum.domain.Project;
import gpi.scrum.domain.UserStory;
import gpi.scrum.dto.UserStoryDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userStory")
@CrossOrigin(origins = "http://localhost:3000")
public class UserStoryController {

    private final UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @GetMapping
    public List<UserStory> getAllUserStories() {
        return userStoryService.getAllUserStories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStory> getUserStoryById(@PathVariable Integer id) {
        Optional<UserStory> userStory = userStoryService.getUserStoryById(id);
        return userStory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable Integer id) {
        return userStoryService.getProject(id);
    }

    @GetMapping("/sprint/{id}")
    public Sprint getSprint(@PathVariable Integer id) {
        return userStoryService.getSprint(id);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<UserStory> createUserStory(@RequestBody UserStoryDto userStory, @PathVariable Integer userId) {
        UserStory createdUserStory = userStoryService.createUserStory(userStory, userId);
        return ResponseEntity.ok(createdUserStory);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserStory> updateUserStory(@PathVariable Integer id, @RequestBody UserStory userStoryDetails) {
        try {
            UserStory updatedUserStory = userStoryService.updateUserStory(id, userStoryDetails);
            return ResponseEntity.ok(updatedUserStory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Integer id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/changeState/{id}")
    public ResponseEntity<UserStory> changeUserStoryState(@PathVariable Integer id, @RequestParam String newState) {
        try {
            UserStory updatedUserStory = userStoryService.changeUserStoryState(id, newState);
            return ResponseEntity.ok(updatedUserStory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userStoryId}/assignUser/{userId}")
    public ResponseEntity<Void> assignUserStoryToUser(@PathVariable Integer userStoryId, @PathVariable Integer userId) {
        userStoryService.assignUserStoryToUser(userStoryId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userStoryId}/removeUser/{userId}")
    public ResponseEntity<Void> removeUserFromUserStory(@PathVariable Integer userStoryId, @PathVariable Integer userId) {
        userStoryService.removeUserFromUserStory(userStoryId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public List<User> getUsersByUserStoryId(@PathVariable Integer id) {
        return userStoryService.getUsersByUserStoryId(id);
    }

}