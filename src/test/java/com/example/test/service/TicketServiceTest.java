package com.example.test.service;

import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import com.example.test.entity.User;
import com.example.test.services.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    private List<Ticket> ticketList;

    @Before
    public void setUp() {
        ticketList = new ArrayList<>();
    }

    @Test
    public void testIssueTicket_TicketIssuedSuccessfully() {
        Ticket ticket = new Ticket();
        ticket.setFrom("London");
        ticket.setTo("France");
        User user = new User();
        user.setFirstName("John");
        ticket.setUser(user);
        String result = ticketService.issueTicket(ticket);
        assertEquals("Ticked issued successfully!!!", result);

    }

    @Test
    public void testIssueTicket_UserAlreadyPurchasedTicket() {
        Ticket existingTicket = new Ticket();
        existingTicket.setFrom("London");
        existingTicket.setTo("France");
        User existingUser = new User();
        existingUser.setFirstName("John");
        existingTicket.setUser(existingUser);

        Ticket ticket = new Ticket();
        ticket.setFrom("London");
        ticket.setTo("France");
        User user = new User();
        user.setFirstName("John");
        ticket.setUser(user);

        ticketList.add(existingTicket);

        String result = ticketService.issueTicket(ticket);

        assertEquals("Ticked issued successfully!!!", result);
        assertFalse(ticketList.contains(ticket));
    }
    @Test
    public void testRemoveUser_UserExistsInList() {
        Ticket ticket1 = new Ticket();
        User user1 = new User();
        user1.setFirstName("John");
        ticket1.setUser(user1);

        Ticket ticket2 = new Ticket();
        User user2 = new User();
        user2.setFirstName("Alice");
        ticket2.setUser(user2);


        ticketList.add(ticket1);
        ticketList.add(ticket2);

        int result = ticketService.removeUser("John");

        assertEquals(2, ticketList.size());
    }
    @Test
    public void testRemoveUser_UserDoesNotExist() {

        Ticket ticket1 = new Ticket();
        User user1 = new User();
        user1.setFirstName("John");
        ticket1.setUser(user1);

        Ticket ticket2 = new Ticket();
        User user2 = new User();
        user2.setFirstName("Alice");
        ticket2.setUser(user2);


        ticketList.add(ticket1);
        ticketList.add(ticket2);

        int result = ticketService.removeUser("Bob");

        assertEquals(0, result);
        assertEquals(2, ticketList.size());
    }

    @Test
    public void testModifyUserTicket_UserDoesNotExist() {
        Ticket ticket = new Ticket();
        ticket.setSection("A");
        User user = new User();
        user.setFirstName("Alice");
        ticket.setUser(user);
        ticketList.add(ticket);
        Ticket modifiedTicket = ticketService.modifyUserTicket("Bob");
        assertNull(modifiedTicket);
    }

}
