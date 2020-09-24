package com.example.ushort.service.generate;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UUIDLinkGenerate implements ShortLinkGenerate {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
