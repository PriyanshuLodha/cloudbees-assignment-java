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

import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import com.example.test.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/ticket")
    public String createTicket(@RequestBody Ticket ticket){
        return ticketService.issueTicket(ticket);
    }
    @GetMapping("/receipt/{userName}")
    public Receipt getReceiptForUser(@PathVariable(name = "userName") String userName){
        return ticketService.getReceipt(userName);
    }
    @GetMapping("/section/{requestedSection}")
    public List<Ticket> getUserWithRequestedSection(@PathVariable(name = "requestedSection") String requestedSection){
        return ticketService.getUsersFromSection(requestedSection);
    }
    @GetMapping("/remove/{userName}")
    public String removeUserFromTrain(@PathVariable(name = "userName") String userName){
        Integer flag= ticketService.removeUser(userName);
        if (flag == 1) {
            return "User deleted successfully!!!";
        }else {
            return "User does not exist";
        }
    }
    @GetMapping("/modify/{userName}")
    public Ticket modifyUserSection(@PathVariable(name = "userName") String userName){
        return ticketService.modifyUserTicket(userName);
    }
}