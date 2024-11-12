package exercise.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testCreateTask() throws Exception {
        var data = new Task();
        data.setTitle(faker.lorem().word());
        data.setDescription(faker.lorem().sentence());
        var request = post("/tasks").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(data));
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void testShowTask() throws Exception {
        var task = prepareTask();
        taskRepository.save(task);

        var response = mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andReturn();
        var body = response.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("id").isEqualTo(1)
        );
    }

    @Test
    public void testUpdateTask() throws Exception {
        var task = prepareTask();
        taskRepository.save(task);

        var data = new HashMap<>();
        data.put("title", "Ololo");
        data.put("description", "Ololo Trololo");

        var request = put("/tasks/" + task.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(data));
        mockMvc.perform(request).andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getTitle()).isEqualTo("Ololo");
        assertThat(task.getDescription()).isEqualTo("Ololo Trololo");
    }

    @Test
    public void testDeleteTask() throws Exception {
        var task = prepareTask();
        taskRepository.save(task);
        var request = delete("/tasks/" + task.getId());
        mockMvc.perform(request).andExpect(status().isOk());
        assertThat(taskRepository.existsById(task.getId())).isFalse();
    }

    private Task prepareTask() {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().sentence())
                .create();
        return task;
    }
    // END
}
