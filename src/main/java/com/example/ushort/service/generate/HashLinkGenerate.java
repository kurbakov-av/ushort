package com.example.ushort.service.generate;

import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class HashLinkGenerate implements ShortLinkGenerate {

    private final String source;

    private final String alg;

    @Override
    public String generate() {
        MessageDigest hashInstance;
        try {
            hashInstance = MessageDigest.getInstance(alg);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing Link Location");
        }

        byte[] hash = hashInstance.digest(source.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(Integer.toHexString(0xFF & b));
        }

        return hex.toString();
    }
}
