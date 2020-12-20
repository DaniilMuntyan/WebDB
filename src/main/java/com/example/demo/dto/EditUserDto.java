package com.example.demo.dto;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {
    private boolean roleUser = false;
    private boolean roleCollector = false;
    private boolean roleAdmin = false;
    private User myUser;
}
