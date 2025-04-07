package gpi.scrum.service;

import gpi.scrum.domain.Project;
import gpi.scrum.domain.User;
import gpi.scrum.domain.UserStory;
import gpi.scrum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User newUser) {
        User user = new User();
        if (userRepository.findByUserName(newUser.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setUserName(newUser.getUserName());
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
    }

    public User loginUser(User loginUser) {
        Optional<User> user = userRepository.findByUserName(loginUser.getUserName());
        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getPassword().equals(loginUser.getPassword())) {
                return existingUser;
            }
            else {
                throw new IllegalArgumentException("Invalid password");
            }
        }
        else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User newUser) {
        Optional<User> user = userRepository.findByUserName(newUser.getUserName());
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setPassword(newUser.getPassword());
            return userRepository.save(existingUser);
        }
        else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public User getUser(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Project> getOwnedProjects(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")).getOwnedProjects();
    }

    public List<Project> getManagedProjects(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")).getManagedProjects();
    }

    public List<Project> getDeveloperProjects(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")).getDeveloperProjects();
    }

    public List<UserStory> getUserStories(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")).getUserStories();
    }

}
