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

    @PostMapping("/{cedula}")
    public Optional<Ticket> addTicketUser(@PathVariable String cedula, @RequestBody Ticket ticket) {
        return userService.addTicketUser(cedula,ticket);
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

    @DeleteMapping("/{cedula}")
    public Optional<User> updateUser(@PathVariable String cedula, @RequestBody User user) {
        user.setCedula(cedula);
        return userService.updateUser(user);
    }

    @GetMapping("/{cedula}/tickets")
    public List<Ticket> getTicketsByUser(@PathVariable String cedula) {
        return userService.getTicketsByUserCedula(cedula);
    }
}
