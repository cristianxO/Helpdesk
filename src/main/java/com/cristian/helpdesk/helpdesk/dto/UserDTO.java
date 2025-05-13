package com.cristian.helpdesk.helpdesk.dto;

import com.cristian.helpdesk.helpdesk.model.Role;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String nit;
    private String name;
    private String email;
    private Role role;
    private List<TicketDTO> ticketsCreated;
    private List<TicketDTO> tickets;
}
