package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.model.Role;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if (!userRepository.existsById(user.getNit()) && securityUtils.isAdmin()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("La cédula se encuentra registrada");
    }

    public List<User> listUser() {
        return userRepository.findAll();
    }

    public Optional<User> searchById(String cedula) {
        return userRepository.findById(cedula);
    }

    public void deleteUser(String cedula) {
        if (securityUtils.isAdmin()) {
            userRepository.deleteById(cedula);
        }
    }

    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getNit());
        if (existingUser.isPresent()) {
            User aux = existingUser.get();
            if (securityUtils.getEmailAuth().equals(aux.getEmail()) || securityUtils.isAdmin()) {
                aux.setName(user.getName());
                aux.setPassword(passwordEncoder.encode(user.getPassword()));
                aux.setRole(user.getRole());
                aux.setEmail(user.getEmail());
                aux.setNit(user.getNit());
                return Optional.of(userRepository.save(user));
            }
        }
        return Optional.empty();
    }

    public List<Ticket> getTicketsByUserCedula(String cedula) {
        Optional<User> user = userRepository.findById(cedula);
        if (user.isPresent()) {
            if (!securityUtils.isClient() || securityUtils.getEmailAuth().equals(user.get().getEmail())) {
                return user.map(User::getTickets).orElse(Collections.emptyList());
            }
        }
        return Collections.emptyList();
    }

    public Optional<Ticket> addTicketUser(String cedula, int id) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        Optional<User> existingUser = userRepository.findById(cedula);
        if (existingUser.isPresent() && existingTicket.isPresent()) {
            User user = existingUser.get();
            Ticket ticket = existingTicket.get();
            ticket.setUser(user);
            user.getTickets().add(existingTicket.get());
            userRepository.save(user);
            return Optional.of(ticketRepository.save(ticket));
        }
        return Optional.empty();
    }
}
