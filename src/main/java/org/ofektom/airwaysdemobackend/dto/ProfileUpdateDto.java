package org.ofektom.airwaysdemobackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDto {
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String gender;
    private String dateOfBirth;
    private String state;
    private String country;
    private String passportNumber;
    private String membershipNo;
}
