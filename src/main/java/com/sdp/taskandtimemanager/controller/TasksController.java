package com.sdp.taskandtimemanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.taskandtimemanager.model.Tasks;
import com.sdp.taskandtimemanager.service.TasksService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TasksController {
    @Autowired
    private TasksService service;

    @GetMapping("/findAll")
    public List<Tasks> findAll() {
        return service.findAllTasks();
    }

    @GetMapping("/findById/{taskId}")
    public Tasks findById(@PathVariable Long taskId) {
        return service.findTaskById(taskId);
    }

       @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Tasks task, @RequestParam Long projectId, @RequestParam Long userId) {
        String result = service.addTask(task, projectId, userId);
        if ("Project not found".equals(result) || "User not found".equals(result)) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/update/{taskId}")
    public Tasks update(@PathVariable Long taskId, @RequestBody Tasks task,@RequestParam Long projectId, @RequestParam Long userId) {
        return service.updateTask(taskId, task,projectId, userId);
    }

    @PatchMapping("/updateSpecific/{taskId}")
    public Tasks patch(@PathVariable Long taskId, @RequestBody Tasks task,@RequestParam Long projectId, @RequestParam Long userId) {
        return service.patchTask(taskId, task,projectId, userId);
    }

    @DeleteMapping("delete/{taskId}")
    public void delete(@PathVariable Long taskId) {
        service.deleteTask(taskId);
    }

}
