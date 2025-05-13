package com.cristian.helpdesk.helpdesk.dto;

import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOUtil {
    public UserDTO convertUserToDTO(User user) {
        return new UserDTO(
                user.getNit(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                convertTicketsToDTO(user.getTicketsCreated()),
                convertTicketsToDTO(user.getTickets())
        );
    }

    public TicketDTO convertTicketToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCreationDate());
    }

    public List<TicketDTO> convertTicketsToDTO(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getPriority(),
                        ticket.getCreationDate())).collect(Collectors.toList());
    }
}
