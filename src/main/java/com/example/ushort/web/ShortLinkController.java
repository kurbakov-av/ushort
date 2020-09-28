package com.example.ushort.web;

import com.example.ushort.domain.ShortLink;
import com.example.ushort.service.ShortLinkCommitRedirectService;
import com.example.ushort.service.ShortLinkService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/link")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    private final ShortLinkCommitRedirectService commitRedirectService;

    @GetMapping
    public Page<ShortLink> links(@PageableDefault Pageable pageable) {
        return shortLinkService.getPage(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/generate")
    public ShortLink generate(@RequestBody @Valid CreateShortLink link) {
        return shortLinkService.create(link.getLocation(), link.getLength(), link.getType());
    }

    @GetMapping(path = "/{trackId}")
    public ResponseEntity<?> redirect(@PathVariable String trackId) {
        ShortLink shortLink = shortLinkService.get(trackId);
        commitRedirectService.commit(shortLink);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .cacheControl(CacheControl.noCache())
                .location(URI.create(shortLink.getLocation()))
                .build();
    }

    @RestControllerAdvice
    static class ErrorAdvice {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler({BindException.class})
        public WebRequestError handleValidateErrors(Throwable e) {
            return new WebRequestError(e.getMessage());
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler({EntityNotFoundException.class})
        public WebRequestError handleNotFound(Throwable e) {
            return new WebRequestError(e.getMessage());
        }

        @Data
        static class WebRequestError {
            private final String message;
        }
    }
}
