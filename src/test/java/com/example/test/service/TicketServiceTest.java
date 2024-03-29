package com.example.test.service;

import com.example.test.entity.ApiResponse;
import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import com.example.test.entity.User;
import com.example.test.services.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;
    private List<Ticket> ticketList;
    @Before
    public void setup() {
        Ticket ticket = new Ticket();
        ticket.setUser(new User("Jane Doe"));
        ticket.setTo("Chicago");
        ticket.setFrom("San Francisco");
        ticket.setPricePaid("150.0");
        ticketList=new ArrayList<>();
        Ticket ticket2 = new Ticket( "New York", "Los Angeles",new User("John Doe"), "200.0", "B");
        ticketList.add(ticket);
        ticketList.add(ticket2);
    }


    @Test
    public void testIssueTicket_ValidUser() {
        // Create a new Ticket object with a valid user
        Ticket ticket = new Ticket();
        ticket.setUser(new User("Priyanshu"));
        ticket.setTo("London");
        ticket.setFrom("France");
        ticket.setPricePaid("150.0");
        ApiResponse apiResponse = ticketService.issueTicket(ticket);
        assertEquals(HttpStatus.OK, apiResponse.getCode());
        assertEquals("User added successfully", apiResponse.getMessage());
        assertNotNull(apiResponse.getResponse());

        assertEquals("A", ticketService.getTicketList().get(0).getSection());
    }

    @Test
    public void testIssueTicket_EmptyUsername() {
        Ticket ticket = new Ticket();
        ticket.setTo("New York");
        ticket.setFrom("Los Angeles");
        ticket.setPricePaid("100.0");

        ApiResponse apiResponse = ticketService.issueTicket(ticket);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiResponse.getCode());
        assertEquals("E-01", apiResponse.getMessage());
        assertEquals("User name should not be null", apiResponse.getResponse());
    }
    @Test
    public void testGetReceipt_InvalidUsername() {
        String firstName = "Invalid User";

        ApiResponse apiResponse = ticketService.getReceipt(firstName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiResponse.getCode());
        assertEquals("E-03", apiResponse.getMessage());
        assertEquals("Please enter valid username", apiResponse.getResponse());
    }
    @Test
    public void testGetUsersFromSection_ValidSectionA() {
        String section = "A";

        ApiResponse apiResponse = ticketService.getUsersFromSection(section);
        assertEquals(HttpStatus.OK, apiResponse.getCode());
        assertEquals("User Section List", apiResponse.getMessage());
        assertNotNull(apiResponse.getResponse()); // Check if user list is present

    }
    @Test
    public void testGetUsersFromSection_ValidSectionB() {
        String section = "B";

        ApiResponse apiResponse = ticketService.getUsersFromSection(section);
        assertEquals(HttpStatus.OK, apiResponse.getCode());
        assertEquals("User Section List", apiResponse.getMessage());
        assertNotNull(apiResponse.getResponse());
    }
    @Test
    public void testGetUsersFromSection_InvalidSection() {
        String section = "C";

        ApiResponse apiResponse = ticketService.getUsersFromSection(section);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiResponse.getCode());
        assertEquals("E-04", apiResponse.getMessage());
        assertEquals("Please enter valid section", apiResponse.getResponse());
    }
    @Test
    public void testRemoveUser_InvalidUsername() {
        String userName = "Nonexistent User";

        ApiResponse apiResponse = ticketService.removeUser(userName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiResponse.getCode());
        assertEquals("E-03", apiResponse.getMessage());
        assertEquals("Please enter valid user name", apiResponse.getResponse());
    }
    @Test
    public void testModifyUserTicket_InvalidUsername() {
        String userName = "Invalid User";

        ApiResponse apiResponse = ticketService.modifyUserTicket(userName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiResponse.getCode());
        assertEquals("E-03", apiResponse.getMessage());
        assertEquals("Please enter valid user name", apiResponse.getResponse());
    }
}
