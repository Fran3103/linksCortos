package com.AcortaLink.acortaLink.servicesImplTest;

import com.AcortaLink.acortaLink.Entities.ShortUrl;
import com.AcortaLink.acortaLink.Repositories.ShortUrlRepository;
import com.AcortaLink.acortaLink.Services.Impl.ShortUrlServiceImpl;
import com.AcortaLink.acortaLink.Services.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class ShortUrlServiceImplTest {

    @Mock
    private ShortUrlRepository repository;

    @InjectMocks
    private ShortUrlServiceImpl service;


    @Test
    void ShortenUrlTest(){
        String original = "https://www.youtube.com/";

        when(repository.save(any(ShortUrl.class))).thenAnswer(invocation -> {
            ShortUrl arg = invocation.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        ShortUrl result = service.shortenUrl(original);

        assertEquals(original, result.getUrlOriginal());
        assertNotNull(result.getCode());
        assertEquals(0L, result.getClickCount());

        verify(repository).save(any(ShortUrl.class));
    }


    @Test
    void getIncrementClicsTest(){
        ShortUrl su =  ShortUrl.builder()
                .id(1L)
                .urlOriginal("https://example.com")
                .code("ABC123")
                .localDateTime(LocalDateTime.now())
                .clickCount(5L)
                .urlShort("http://localhost:8080/ABC123")
                .build();;


        when(repository.findByCode("ABC123")).thenReturn(Optional.of(su));

        ShortUrl updated = service.getAndIncrement("ABC123");

        assertEquals(6L, updated.getClickCount());
        verify(repository).findByCode("ABC123");

    }
}
