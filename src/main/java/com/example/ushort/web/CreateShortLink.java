package com.example.ushort.web;

import com.example.ushort.service.generate.GenerationType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateShortLink {

    @NotNull
    @Pattern(regexp = "^(http|https?://)([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$")
    private final String location;

    @NotNull
    private final GenerationType type;
}