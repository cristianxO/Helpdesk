package com.cristian.helpdesk.helpdesk.controller;

import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/update")
    public Optional<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PatchMapping("/{cedula}/{id}")
    public Optional<Ticket> addTicketUser(@PathVariable String cedula, @PathVariable int id) {
        return userService.addTicketUser(cedula,id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.listUser();
    }

    @GetMapping("/{cedula}")
    public Optional<User> getUsersById(@PathVariable String cedula) {
        return userService.searchById(cedula);
    }

    @DeleteMapping("/{cedula}")
    public void deleteUser(@PathVariable String cedula) {
        userService.deleteUser(cedula);
    }

    @GetMapping("/email-existe")
    public boolean verifyEmail(@RequestParam String email) {
        return userService.existEmail(email);
    }

    @GetMapping("/{cedula}/tickets")
    public List<Ticket> getTicketsByUser(@PathVariable String cedula) {
        return userService.getTicketsByUserCedula(cedula);
    }
}
