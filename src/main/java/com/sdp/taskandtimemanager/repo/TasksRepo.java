package com.sdp.taskandtimemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdp.taskandtimemanager.model.Tasks;

public interface TasksRepo extends JpaRepository<Tasks,Long> {
}
