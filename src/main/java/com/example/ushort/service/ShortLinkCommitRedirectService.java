package com.example.ushort.service;

import com.example.ushort.domain.ShortLink;
import com.example.ushort.domain.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShortLinkCommitRedirectService {

    private final ShortLinkRepository shortLinkRepository;

    @Transactional
    public void commit(ShortLink link) {
        link.setRedirectCount(link.getRedirectCount() + 1);
        shortLinkRepository.save(link);
    }
}
