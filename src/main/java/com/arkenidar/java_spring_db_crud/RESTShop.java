package com.arkenidar.java_spring_db_crud;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

// RESTful API for Shop REST resource
@RestController
@RequestMapping("/api/shop")
public class RESTShop {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{id}")
    public Map<String, Object> readOne(@PathVariable Long id) {
        return jdbcTemplate.queryForMap("select * from shops where id=?", id);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> readAll() {
        return jdbcTemplate.queryForList("select * from shops");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        int numRowsaAffected = jdbcTemplate.update("DELETE FROM shops WHERE (id = ?);", id);
        Map<String, String> form = new java.util.HashMap<>();
        form.put("@info", "DELETE request received (DELETE) ID: " + id);
        form.put("success", numRowsaAffected > 0 ? "true" : "false");
        return form;
    }

    @PostMapping("")
    public Map<String, String> create(
            @RequestParam Map<String, String> form) {
        String sql = "INSERT INTO shops (nome, indirizzo, civico) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql,
                form.get("nome"), form.get("indirizzo"), form.get("civico"));
        form.put("@info", "POST request received (CREATE)");
        return form;
    }

    @PutMapping("/{id}")
    public Map<String, String> update(
            @PathVariable Long id,
            @RequestParam Map<String, String> form) {
        jdbcTemplate.update("UPDATE shops SET nome =?, indirizzo =?, civico =? WHERE (id =?);",
                form.get("nome"), form.get("indirizzo"), form.get("civico"), id);
        form.put("@info", "PUT request received (UPDATE) ID: " + id);
        return form;
    }

}
