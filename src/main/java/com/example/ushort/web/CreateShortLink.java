package com.example.ushort.web;

import com.example.ushort.service.generate.GenerationType;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class CreateShortLink {

    @NotNull
    @Pattern(regexp = "^(http|https?://)([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$")
    String location;

    @Min(4)
    Integer length;

    @NotNull
    GenerationType type;
}
