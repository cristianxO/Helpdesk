package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.dto.DTOUtil;
import com.cristian.helpdesk.helpdesk.dto.TicketDTO;
import com.cristian.helpdesk.helpdesk.dto.UserDTO;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private DTOUtil dtoUtil;

    public UserDTO saveUser(User user) {
        if (!userRepository.existsById(user.getNit()) && securityUtils.isAdmin()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setTickets(null);
            user.setTicketsCreated(null);
            userRepository.save(user);
            return dtoUtil.convertUserToDTO(user);
        }
        throw new IllegalArgumentException("La cédula se encuentra registrada");
    }

    public List<UserDTO> listUser() {
        return userRepository.findAll().stream()
                .map(user -> dtoUtil.convertUserToDTO(user))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> searchById(String cedula) {
        return userRepository.findById(cedula)
                .map(user -> dtoUtil.convertUserToDTO(user));
    }

    public void deleteUser(String cedula) {
        if (securityUtils.isAdmin()) {
            userRepository.deleteById(cedula);
        }
    }

    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public Optional<UserDTO> updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getNit());
        if (existingUser.isPresent()) {
            User aux = existingUser.get();
            if (securityUtils.getEmailAuth().equals(aux.getEmail()) || securityUtils.isAdmin()) {
                aux.setName(user.getName());
                aux.setPassword(passwordEncoder.encode(user.getPassword()));
                aux.setRole(user.getRole());
                aux.setEmail(user.getEmail());
                aux.setNit(user.getNit());
                userRepository.save(aux);
                return Optional.of(dtoUtil.convertUserToDTO(aux));
            }
        }
        return Optional.empty();
    }

    public List<TicketDTO> getTicketsByUserCedula(String cedula) {
        Optional<User> user = userRepository.findById(cedula);
        if (user.isPresent()) {
            if (!securityUtils.isClient() || securityUtils.getEmailAuth().equals(user.get().getEmail())) {
                return user.map(aux -> dtoUtil.convertTicketsToDTO(aux.getTickets())).orElse(Collections.emptyList());
            }
        }
        return Collections.emptyList();
    }

    public Optional<TicketDTO> addTicketUser(String cedula, int id) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        Optional<User> existingUser = userRepository.findById(cedula);
        if (existingUser.isPresent() && existingTicket.isPresent()) {
            User user = existingUser.get();
            Ticket ticket = existingTicket.get();
            ticket.setUser(user);
            user.getTickets().add(existingTicket.get());
            userRepository.save(user);
            ticketRepository.save(ticket);
            return Optional.of(dtoUtil.convertTicketToDTO(ticket));
        }
        return Optional.empty();
    }
}
