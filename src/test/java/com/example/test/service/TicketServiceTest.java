package com.example.test.service;

import com.example.test.entity.ApiResponse;
import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import com.example.test.entity.User;
import com.example.test.services.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testIssueTicket_ValidTicket() {
        Ticket ticket = new Ticket("New York", "London", new User("John"), "$50", "A");
        ApiResponse response = ticketService.issueTicket(ticket);

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("User added successfully", response.getMessage());
        assertEquals(ticket, response.getResponse());
    }
    @Test
    public void testIssueTicket_NullUser() {
        Ticket ticket = new Ticket("New York", "London", null, "$50", "A");
        ApiResponse response = ticketService.issueTicket(ticket);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("E-01", response.getMessage());
        assertEquals("User name should not be null", response.getResponse());
    }
    @Test
    public void testGetReceipt_ValidUser() {
        Ticket ticket = new Ticket("New York", "London", new User("Alice"), "$50", "A");
        ticketService.issueTicket(ticket);

        ApiResponse response = ticketService.getReceipt("Alice");

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("User Receipt", response.getMessage());
        Receipt receipt = (Receipt) response.getResponse();
        assertEquals(ticket.getFrom(), receipt.getFrom());
        assertEquals(ticket.getTo(), receipt.getTo());
        assertEquals(ticket.getUser().getFirstName(), receipt.getFirstName());
        assertEquals(ticket.getSection(), receipt.getSection());
        assertEquals(ticket.getPricePaid(), receipt.getPricePaid());
    }
    @Test
    public void testGetReceipt_InvalidUser() {
        ApiResponse response = ticketService.getReceipt("Bob");  // Non-existent user

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("E-03", response.getMessage());
        assertEquals("Please enter valid username", response.getResponse());
    }
    @Test
    public void testGetUsersFromSection_ValidSection() {
        Ticket ticketA = new Ticket("New York", "London", new User("Alice"), "$50", "A");
        Ticket ticketB = new Ticket("Paris", "Tokyo", new User("Bob"), "$70", "B");
        ticketService.issueTicket(ticketA);
        ticketService.issueTicket(ticketB);

        ApiResponse responseA = ticketService.getUsersFromSection("A");
        ApiResponse responseB = ticketService.getUsersFromSection("B");

        assertEquals(HttpStatus.OK, responseA.getCode());
        List<Ticket> ticketsA = (List<Ticket>) responseA.getResponse();
        assertEquals(1, ticketsA.size()); // Only ticketA has section "A"
        assertEquals(ticketA, ticketsA.get(0));

        assertEquals(HttpStatus.OK, responseB.getCode());
        List<Ticket> ticketsB = (List<Ticket>) responseB.getResponse();
        assertEquals(1, ticketsB.size()); // Only ticketB has section "B"
        assertEquals(ticketB, ticketsB.get(0));
    }
    @Test
    public void testRemoveUser_ValidUser() {
        Ticket ticket = new Ticket("New York", "London", new User("Charlie"), "$50", "A");
        ticketService.issueTicket(ticket);

        ApiResponse response = ticketService.removeUser("Charlie");

        assertEquals(HttpStatus.OK, response.getCode());
        assertEquals("removed", response.getMessage());
        assertEquals("User removed successfully", response.getResponse());
    }
    @Test
    public void testRemoveUser_InvalidUser() {
        ApiResponse response = ticketService.removeUser("David");  // Non-existent user

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("E-03", response.getMessage());
        assertEquals("Please enter valid user name", response.getResponse());
    }
    @Test
    public void testModifyUserTicket_InvalidUser() {
        ApiResponse response = ticketService.modifyUserTicket("Frank");  // Non-existent user

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getCode());
        assertEquals("E-03", response.getMessage());
        assertEquals("Please enter valid user name", response.getResponse());
    }
}
