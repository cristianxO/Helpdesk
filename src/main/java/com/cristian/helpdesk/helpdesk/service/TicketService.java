package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.dto.DTOUtil;
import com.cristian.helpdesk.helpdesk.dto.TicketDTO;
import com.cristian.helpdesk.helpdesk.model.Status;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.repository.TicketRepository;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DTOUtil dtoUtil;

    public TicketDTO createTicket(Ticket ticket) {
        ticket.setStatus(Status.OPEN);
        ticket.setCreationDate(LocalDateTime.now());
        ticket.setCreator(userRepository.findByEmail(SecurityUtils.getEmailAuth()).get());
        ticketRepository.save(ticket);
        return dtoUtil.convertTicketToDTO(ticket);
    }

    public List<TicketDTO> listTicket() {
        return ticketRepository.findAll().stream()
                .map(ticket -> dtoUtil.convertTicketToDTO(ticket)).collect(Collectors.toList());
    }

    public Optional<TicketDTO> searchById(int id) {
        return ticketRepository.findById(id)
                .map(ticket -> dtoUtil.convertTicketToDTO(ticket));
    }

    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }

    public Optional<TicketDTO> updateTicket(Ticket ticket) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticket.getId());
        if (existingTicket.isPresent()) {
            Ticket aux = existingTicket.get();
            aux.setPriority(ticket.getPriority());
            aux.setDescription(ticket.getDescription());
            aux.setTitle(ticket.getTitle());
            ticketRepository.save(aux);
            return Optional.of(dtoUtil.convertTicketToDTO(aux));
        }
        return Optional.empty();
    }

    public Optional<TicketDTO> updateStatusTicket(int id, Status status) {
        if (SecurityUtils.isTecnico() || SecurityUtils.isAdmin()) {
            Optional<Ticket> existingTicket = ticketRepository.findById(id);
            if (existingTicket.isPresent()) {
                Ticket ticket = existingTicket.get();
                ticket.setStatus(status);
                ticketRepository.save(ticket);
                return Optional.of(dtoUtil.convertTicketToDTO(ticket));
            }
        }
        return Optional.empty();
    }
}
