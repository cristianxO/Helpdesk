package com.cristian.helpdesk.helpdesk.dto;

import com.cristian.helpdesk.helpdesk.model.Priority;
import com.cristian.helpdesk.helpdesk.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime creationDate;
}
