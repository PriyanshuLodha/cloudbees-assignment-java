package com.example.test.services;

import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TicketService {
    List<Ticket> ticketList=new ArrayList<>();

    Integer count=0;
    public String issueTicket(Ticket ticket){
        for(Ticket existingTicket: ticketList){
            if((ticket.getTo().equals(existingTicket.getTo()))&&(ticket.getFrom().equals(existingTicket.getFrom()))&&(ticket.getUser().getFirstName().equals(existingTicket.getUser().getFirstName()))){
                return "User already purchased ticket!!!";
            }
        }
        if(count%2==0){
            ticket.setSection("A");
        }else {
            ticket.setSection("B");
        }
        count++;
        ticketList.add(ticket);
        return "Ticked issued successfully!!!";
    }

    public Receipt getReceipt(String firstName) {
        for (Ticket existingTicket : ticketList) {
            if (existingTicket.getUser().getFirstName().equals(firstName)) {
                Receipt userReceipt = new Receipt();
                userReceipt.setTo(existingTicket.getTo());
                userReceipt.setFrom(existingTicket.getFrom());
                userReceipt.setFirstName(existingTicket.getUser().getFirstName());
                userReceipt.setSection(existingTicket.getSection());
                userReceipt.setPricePaid(existingTicket.getPricePaid());
                return userReceipt;
            }
        }
        return null;
    }

    public List<Ticket> getUsersFromSection(String section){
        List<Ticket> userSectionList=new ArrayList<>();
        for(Ticket existingTicket:ticketList){
            if(existingTicket.getSection().equals(section)){
                userSectionList.add(existingTicket);
            }
        }
        return userSectionList;
    }
    public Integer removeUser(String userName){
        Iterator<Ticket> iterator = ticketList.iterator();
        Integer flag = 0;
        while (iterator.hasNext()) {
            Ticket existingTicket = iterator.next();
            if (existingTicket.getUser().getFirstName().equals(userName)) {
                iterator.remove();
                flag = 1;
            }
        }
        return flag;
    }
    public Ticket modifyUserTicket(String userName){
        Ticket modifiedTicket = null;
        for(Ticket existingTicket : ticketList){
            if(existingTicket.getUser().getFirstName().equals(userName)){
                if(existingTicket.getSection().equals("A")){
                    existingTicket.setSection("B");
                } else {
                    existingTicket.setSection("A");
                }
                modifiedTicket = existingTicket;
                break;
            }
        }
        return modifiedTicket;
    }
}
