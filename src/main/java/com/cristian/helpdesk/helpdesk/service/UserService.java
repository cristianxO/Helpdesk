package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> listUser() {
        return userRepository.findAll();
    }

    public Optional<User> searchById(String cedula) {
        return userRepository.findById(cedula);
    }

    public void deleteUser(String cedula) {
        userRepository.deleteById(cedula);
    }

    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getCedula());
        if (existingUser.isPresent()) {
            return Optional.of(userRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    public List<Ticket> getTicketsByUserCedula(String cedula) {
        Optional<User> user = userRepository.findById(cedula);
        return user.map(User::getTickets).orElse(Collections.emptyList());
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
