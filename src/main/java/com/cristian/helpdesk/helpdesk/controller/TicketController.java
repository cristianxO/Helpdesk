package com.cristian.helpdesk.helpdesk.controller;

import com.cristian.helpdesk.helpdesk.model.Status;
import com.cristian.helpdesk.helpdesk.model.Ticket;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/update")
    public Optional<Ticket> updateTicket(@RequestBody Ticket ticket) {
        return ticketService.updateTicket(ticket);
    }

    @PatchMapping("/{id}/{status}")
    public Optional<Ticket> updateStatusTicket(@PathVariable int id, @PathVariable Status status) {
        return ticketService.updateStatusTicket(id,status);
    }

    @GetMapping
    public List<Ticket> getTickets() {
        return ticketService.listTicket();
    }

    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable int id) {
        return ticketService.searchById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        ticketService.deleteTicket(id);
    }

}
