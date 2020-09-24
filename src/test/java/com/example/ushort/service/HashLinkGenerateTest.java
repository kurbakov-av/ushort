package com.example.ushort.service;

import com.example.ushort.service.generate.HashLinkGenerate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HashLinkGenerateTest {

    HashLinkGenerate linkGenerate;

    @BeforeEach
    void setup() {
        String location = "https://example.com";
        linkGenerate = new HashLinkGenerate(location, "SHA-256");
    }

    @Test
    void generate() {
        String actual = linkGenerate.generate();

        assertNotNull(actual);
    }
}