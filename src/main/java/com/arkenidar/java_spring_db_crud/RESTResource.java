package com.arkenidar.java_spring_db_crud;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Map;

// RESTful API for example REST resource
@RestController
@RequestMapping("/api/resource")
public class RESTResource {

    @GetMapping("/{id}")
    public String read(@PathVariable String id) {
        return "GET request received (READ) ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return "DELETE request received (DELETE) ID: " + id;
    }

    @PostMapping("")
    public Map<String, String> create(
            @RequestParam Map<String, String> form) {
        form.put("@info", "POST request received (CREATE)");
        return form;
    }

    @PutMapping("/{id}")
    public Map<String, String> update(
            @PathVariable String id,
            @RequestParam Map<String, String> form) {
        form.put("@info", "PUT request received (UPDATE) ID: " + id);
        return form;
    }

}
