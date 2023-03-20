package com.msbills.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillUserResponse {

    private String billId;
    private Date billingDate;
    private Double totalPrice;
    private User user;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private String id;
        private String username;
        private String avatar;
        private String email;
        private String firstName;
        private String lastName;
    }

}