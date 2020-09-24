package com.example.ushort.service;

import com.example.ushort.domain.ShortLink;
import com.example.ushort.service.generate.GenerationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShortLinkService {
    ShortLink create(String location, GenerationType generationType);

    ShortLink get(String trackId);

    Page<ShortLink> getPage(Pageable pageable);
}
