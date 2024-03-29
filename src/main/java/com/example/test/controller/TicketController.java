/*
   CLOUDBEES Project

   Overview:
   This file is part of the CLOUDBEES project.

   Author: Priyanshu Lodha

   These are the Api's that need to be called for the desired result described in assigment.

   Date: 2-FEB-2024

   Thank You!!!
*/

package com.example.test.controller;

import com.example.test.entity.ApiResponse;
import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import com.example.test.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudbees")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/ticket")
    public ApiResponse createTicket(@RequestBody Ticket ticket){
        return ticketService.issueTicket(ticket);
    }
    @GetMapping("/receipt/{userName}")
    public ApiResponse getReceiptForUser(@PathVariable(name = "userName") String userName){
        return ticketService.getReceipt(userName);
    }
    @GetMapping("/section/{requestedSection}")
    public ApiResponse getUserWithRequestedSection(@PathVariable(name = "requestedSection") String requestedSection){
        return ticketService.getUsersFromSection(requestedSection);
    }
    @GetMapping("/remove/{userName}")
    public ApiResponse removeUserFromTrain(@PathVariable(name = "userName") String userName){
        return ticketService.removeUser(userName);
    }
    @GetMapping("/modify/{userName}")
    public ApiResponse modifyUserSection(@PathVariable(name = "userName") String userName){
        return ticketService.modifyUserTicket(userName);
    }
}