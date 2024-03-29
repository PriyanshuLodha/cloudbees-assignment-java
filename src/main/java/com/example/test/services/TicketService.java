package com.example.test.services;

import com.example.test.entity.ApiResponse;
import com.example.test.entity.Receipt;
import com.example.test.entity.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Getter
@Setter
public class TicketService {
    @Autowired
    List<Ticket> ticketList=new ArrayList<>();

    Integer count=0;

    public ApiResponse issueTicket(Ticket ticket){
        if(ticket.getUser()==null){
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-01","User name should not be null");
        }
        for(Ticket existingTicket: ticketList){
            if((ticket.getTo().equals(existingTicket.getTo()))&&(ticket.getFrom().equals(existingTicket.getFrom()))&&(ticket.getUser().getFirstName().equals(existingTicket.getUser().getFirstName()))){
                return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-02","User Already Exist");
            }
        }
        if(count%2==0){
            ticket.setSection("A");
        }else {
            ticket.setSection("B");
        }
        count++;
        ticketList.add(ticket);
        return new ApiResponse(HttpStatus.OK,"User added successfully",ticket);
    }

    public ApiResponse getReceipt(String firstName) {
        for (Ticket existingTicket : ticketList) {
            if (existingTicket.getUser().getFirstName().equals(firstName)) {
                Receipt userReceipt = new Receipt();
                userReceipt.setTo(existingTicket.getTo());
                userReceipt.setFrom(existingTicket.getFrom());
                userReceipt.setFirstName(existingTicket.getUser().getFirstName());
                userReceipt.setSection(existingTicket.getSection());
                userReceipt.setPricePaid(existingTicket.getPricePaid());
                return new ApiResponse(HttpStatus.OK,"User Receipt",userReceipt);
            }
        }
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-03","Please enter valid username");
    }

    public ApiResponse getUsersFromSection(String section){
//        if(section!="A"||section!="B"){
//            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-04","Please enter valid section");
//        }
        System.out.println(section);
        if(section.equals("A")||section.equals("B")){
            List<Ticket> userSectionList=new ArrayList<>();
            for(Ticket existingTicket:ticketList){
                if(existingTicket.getSection().equals(section)){
                    userSectionList.add(existingTicket);
                }
            }
            return new ApiResponse(HttpStatus.OK,"User Section List",userSectionList);
        }else {
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-04","Please enter valid section");
        }
    }
    public ApiResponse removeUser(String userName){
        Iterator<Ticket> iterator = ticketList.iterator();
        Integer flag = 0;
        while (iterator.hasNext()) {
            Ticket existingTicket = iterator.next();
            if (existingTicket.getUser().getFirstName().equals(userName)) {
                iterator.remove();
                flag = 1;
            }
        }
        if(flag==0){
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-03","Please enter valid user name");
        }
        return new ApiResponse(HttpStatus.OK,"removed","User removed successfully");
    }
    public ApiResponse modifyUserTicket(String userName){
        Ticket modifiedTicket = null;
        int flag=0;
        for(Ticket existingTicket : ticketList){
            if(existingTicket.getUser().getFirstName().equals(userName)){
                flag=1;
                if(existingTicket.getSection().equals("A")){
                    existingTicket.setSection("B");
                } else {
                    existingTicket.setSection("A");
                }
                modifiedTicket = existingTicket;
                break;
            }
        }
        if(flag==0){
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"E-03","Please enter valid user name");
        }
        else {
         return new ApiResponse(HttpStatus.OK,"Ticket modified successfully",modifiedTicket);
        }
    }
}
