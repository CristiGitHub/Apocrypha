package com.Apocrypha.Apocrypha.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersValues {

    private int Mathematics;
    private int Informatics;
    private int Physics;
}
