package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.model.Status;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtils securityUtils;

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus(Status.OPEN);
        ticket.setCreationDate(LocalDateTime.now());
        ticket.setCreator(userRepository.findByEmail(securityUtils.getEmailAuth()).get());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listTicket() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> searchById(int id) {
        return ticketRepository.findById(id);
    }

    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }

    public Optional<Ticket> updateTicket(Ticket ticket) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticket.getId());
        if (existingTicket.isPresent()) {
            Ticket aux = existingTicket.get();
            aux.setPriority(ticket.getPriority());
            aux.setDescription(ticket.getDescription());
            aux.setTitle(ticket.getTitle());
            return Optional.of(ticketRepository.save(aux));
        }
        return Optional.empty();
    }

    public Optional<Ticket> updateStatusTicket(int id, Status status) {
        if (securityUtils.isTecnico() || securityUtils.isAdmin()) {
            Optional<Ticket> existingTicket = ticketRepository.findById(id);
            if (existingTicket.isPresent()) {
                Ticket ticket = existingTicket.get();
                ticket.setStatus(status);
                return Optional.of(ticketRepository.save(ticket));
            }
        }
        return Optional.empty();
    }
}
