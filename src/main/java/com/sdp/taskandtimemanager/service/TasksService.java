package com.sdp.taskandtimemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.taskandtimemanager.model.Projects;
import com.sdp.taskandtimemanager.model.Tasks;
import com.sdp.taskandtimemanager.model.Users;
import com.sdp.taskandtimemanager.repo.ProjectsRepo;
import com.sdp.taskandtimemanager.repo.TasksRepo;
import com.sdp.taskandtimemanager.repo.UsersRepo;

@Service
public class TasksService {

    @Autowired
    private TasksRepo repo;
    @Autowired
    private ProjectsRepo prepo;
    @Autowired
    private UsersRepo urepo;

    public List<Tasks> findAllTasks() {
        return repo.findAll();
    }

    public Tasks findTaskById(Long taskId) {
        return repo.findById(taskId).orElse(null);
    }

    public String addTask(Tasks task, Long projectId, Long userId) {
        Projects project = prepo.findById(projectId).orElse(null);
        Users user = urepo.findById(userId).orElse(null);

        if (project == null) {
            return "Project not found";
        }
        if (user == null) {
            return "User not found";
        }

        task.setProject(project);
        task.setMember(user);

        repo.save(task);
        return "Task added";
    }

    public Tasks updateTask(Long taskId, Tasks task, Long projectId, Long userId) {
        Optional<Tasks> optionalTasks = repo.findById(taskId);
        if (optionalTasks.isPresent()) {
            Tasks existingTask = optionalTasks.get();

            existingTask.setTaskname(task.getTaskname());
            existingTask.setTaskdescription(task.getTaskdescription());
            existingTask.setTaskstatus(task.getTaskstatus());
            existingTask.setTaskpriority(task.getTaskpriority());

            Projects project = prepo.findById(projectId).orElse(null);
            Users user = urepo.findById(userId).orElse(null);

            if (project != null) {
                existingTask.setProject(project);
            }
            if (user != null) {
                existingTask.setMember(user);
            }

            return repo.save(existingTask);
        }
        return null;
    }

    public Tasks patchTask(Long taskId, Tasks task, Long projectId, Long userId) {
        Optional<Tasks> optionalTask = repo.findById(taskId);
        if (optionalTask.isPresent()) {
            Tasks existingTask = optionalTask.get();

            if (task.getTaskname() != null) {
                existingTask.setTaskname(task.getTaskname());
            }

            if (task.getTaskdescription() != null) {
                existingTask.setTaskdescription(task.getTaskdescription());
            }

            if (task.getTaskstatus() != null) {
                existingTask.setTaskstatus(task.getTaskstatus());
            }

            if (task.getTaskpriority() != null) {
                existingTask.setTaskpriority(task.getTaskpriority());
            }

            Projects project = prepo.findById(projectId).orElse(null);
            Users user = urepo.findById(userId).orElse(null);

            if (project != null) {
                existingTask.setProject(project);
            }
            if (user != null) {
                existingTask.setMember(user);
            }

            return repo.save(existingTask);
        }
        return task;
    }

    public void deleteTask(Long taskId) {
        repo.deleteById(taskId);
    }

}
