package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    UserProperties userProperties;

    // Все пользователи
    private List<User> users = Data.getUsers();

    // BEGIN
    @GetMapping("/admins")
    public List<String> getAdmins() {
        List<User> admins = users.stream().filter(i->userProperties.getAdmins().contains(i.getEmail())).toList();
        List<String> adminsName = new ArrayList<>();
        admins.forEach(i -> adminsName.add(i.getName()));
        return adminsName.stream().sorted().toList();
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
