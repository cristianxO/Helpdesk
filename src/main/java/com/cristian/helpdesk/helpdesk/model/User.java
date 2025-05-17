package com.cristian.helpdesk.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String nit;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Ticket> ticketsCreated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<Ticket> tickets;
}
