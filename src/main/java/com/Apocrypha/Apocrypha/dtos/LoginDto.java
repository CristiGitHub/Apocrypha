package com.Apocrypha.Apocrypha.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    private String email;
    @NotNull
    private String password;
}
