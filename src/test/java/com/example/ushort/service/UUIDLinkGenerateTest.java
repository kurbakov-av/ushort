package com.example.ushort.service;

import com.example.ushort.service.generate.UUIDLinkGenerate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UUIDLinkGenerateTest {

    UUIDLinkGenerate linkGenerate;

    @BeforeEach
    void setup() {
        linkGenerate = new UUIDLinkGenerate();
    }

    @Test
    void generate() {
        String actual = linkGenerate.generate();

        assertNotNull(actual);
    }
}