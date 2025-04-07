package gpi.scrum.controller;


import gpi.scrum.domain.Project;
import gpi.scrum.domain.User;
import gpi.scrum.domain.UserStory;
import gpi.scrum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{id}")
    public List<Project> getProjectsByOwner(@PathVariable Integer id) {
        return userService.getOwnedProjects(id);
    }

    @GetMapping("/developer/{id}")
    public List<Project> getProjectsByDeveloper(@PathVariable Integer id) {
        return userService.getDeveloperProjects(id);
    }

    @GetMapping("/scrummaster/{id}")
    public List<Project> getProjectsByScrumMaster(@PathVariable Integer id) {
        return userService.getManagedProjects(id);
    }

    @GetMapping("/userstories/{id}")
    public List<UserStory> getUserStories(@PathVariable Integer id) {
        return userService.getUserStories(id);
    }

}
