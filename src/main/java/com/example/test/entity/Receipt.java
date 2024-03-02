package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private String from;
    private String to;
    private String pricePaid;
    private String Section;
    private String firstName;
}
