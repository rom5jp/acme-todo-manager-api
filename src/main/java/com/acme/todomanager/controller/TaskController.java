package com.acme.todomanager.controller;

import com.acme.todomanager.entity.Task;
import com.acme.todomanager.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private ITaskRepository repository;

    @GetMapping("tasks")
    public List<Task> List() {
        return this.repository.findAllByOrderByIdAsc();
    }

    @PostMapping("tasks")
    public Task Create(@RequestBody Task task) {
        return this.repository.save(task);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<Task> Update(@PathVariable("id") Long id, @RequestBody Task task) {
        Optional<Task> fetchedTask = this.repository.findById(id);

        if (!id.equals(task.getId())) {
            return new ResponseEntity("The PathVariable ID must be equals to the entity ID.", HttpStatus.BAD_REQUEST);
        }
        else if (fetchedTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.repository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Task> Delete(@PathVariable("id") Long id) {
        Optional<Task> fetchedTask = this.repository.findById(id);
        if (fetchedTask.isPresent()) {
            this.repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
