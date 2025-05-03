package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket saveTicket(Ticket ticket) {
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
            User user = ticket.getUser();
            if (user != null && user.getCedula() != null) {
                if (!user.getCedula().isEmpty()) {
                    Optional<User> existingUser = userRepository.findById(ticket.getUser().getCedula());
                    if (existingUser.isPresent()) {
                        return Optional.of(ticketRepository.save(ticket));
                    }else
                        return Optional.empty();
                }else
                    return Optional.empty();
            }else{
                ticket.setUser(null);
                return Optional.of(ticketRepository.save(ticket));
            }
        }
        return Optional.empty();
    }
}
