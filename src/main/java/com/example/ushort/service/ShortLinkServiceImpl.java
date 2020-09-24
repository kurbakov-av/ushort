package com.example.ushort.service;

import com.example.ushort.domain.ShortLink;
import com.example.ushort.domain.ShortLinkRepository;
import com.example.ushort.service.generate.GenerationType;
import com.example.ushort.service.generate.HashLinkGenerate;
import com.example.ushort.service.generate.ShortLinkGenerate;
import com.example.ushort.service.generate.UUIDLinkGenerate;
import com.example.ushort.service.search.ShortLinkSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService {

    private static final int MAX_ATTEMPTS = 1000;

    private final ShortLinkRepository shortLinkRepository;

    private final ShortLinkSearchService jpaShortLinkSearch;

    @Override
    public ShortLink create(String location, GenerationType generationType) {
        String trackId;
        int trackIdLength = 4;
        int currentAttempt = 1;

        ShortLinkGenerate generator = getGenerator(location, generationType);
        do {
            if (currentAttempt++ / 100 > 0) {
                trackIdLength++;
            }
            trackId = generator.generate().substring(0, trackIdLength);
        } while (currentAttempt < MAX_ATTEMPTS && shortLinkRepository.existsById(trackId));

        ShortLink shortLink = new ShortLink();
        shortLink.setTrack(trackId);
        shortLink.setLocation(location);
        try {
            shortLink.setHost(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException();
        }

        return shortLinkRepository.save(shortLink);
    }

    private ShortLinkGenerate getGenerator(String location, GenerationType type) {
        switch (type) {
            case UUID:
                return new UUIDLinkGenerate();
            case SHA_256:
                return new HashLinkGenerate(location, "SHA-256");
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ShortLink get(String trackId) {
        return jpaShortLinkSearch.search(trackId);
    }

    @Override
    public Page<ShortLink> getPage(Pageable pageable) {
        return shortLinkRepository.findAll(pageable);
    }
}
