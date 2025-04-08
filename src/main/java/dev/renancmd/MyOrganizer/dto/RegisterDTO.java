package dev.renancmd.MyOrganizer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDTO {

    private String name;
    private String email;
    private String password;

}
