package com.arkenidar.java_spring_db_crud;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Routes {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public ModelAndView userInterface() {
        // HTML UI for REST
        ModelAndView modelAndView = new ModelAndView("shops-rest-ui");
        return modelAndView;
    }

    @GetMapping("/shops/all.html")
    public String homepage(Model model) {
        model.addAttribute("shops", allShops());
        return "home";
    }

    @GetMapping("/shops/all.json")
    public @ResponseBody List<Map<String, Object>> allShops() {
        return jdbcTemplate.queryForList("select * from shops");

    }

    @GetMapping("/shops/delete.action")
    public String deleteShop(@RequestParam long id) {
        jdbcTemplate.update("DELETE FROM shops WHERE (id = ?);", id);
        return "redirect:/";
    }

    @GetMapping("/shops/new.html")
    public String newShop() {
        return "shop-editor-create";
    }

    @PostMapping("/shops/create.action")
    public String createShop(@RequestParam Map<String, String> post) {
        String sql = "INSERT INTO shops (nome, indirizzo, civico) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql,
                post.get("nome"), post.get("indirizzo"), post.get("civico"));
        return "redirect:/";
    }

    @GetMapping("/shops/edit.html")
    public String postMethodName(Model model, @RequestParam long id) {
        Map<String, Object> shop = jdbcTemplate.queryForMap("select * from shops where id=?", id);
        model.addAttribute("shop", shop);
        return "shop-editor-update";
    }

    @PostMapping("/shops/update.action")
    public String updateShop(@RequestParam Map<String, String> post) {
        Integer id = Integer.parseInt(post.get("id"));
        jdbcTemplate.update("UPDATE shops SET nome =?, indirizzo =?, civico =? WHERE (id =?);",
                post.get("nome"), post.get("indirizzo"), post.get("civico"), id);
        return "redirect:/";
    }

}
