package com.example.ushort.service.search;

import com.example.ushort.domain.ShortLink;
import com.example.ushort.domain.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class JpaShortLinkSearch implements ShortLinkSearchService {

    private final ShortLinkRepository shortLinkRepository;

    @Override
    public ShortLink search(String trackId) {
        return shortLinkRepository.findById(trackId)
                .orElseThrow(() -> new EntityNotFoundException("Short link not found by trackId: " + trackId));
    }
}
