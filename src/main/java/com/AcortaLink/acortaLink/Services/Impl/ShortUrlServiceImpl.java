package com.AcortaLink.acortaLink.Services.Impl;

import com.AcortaLink.acortaLink.Entities.ShortUrl;
import com.AcortaLink.acortaLink.Repositories.ShortUrlRepository;
import com.AcortaLink.acortaLink.Services.ShortUrlService;
import com.AcortaLink.acortaLink.exceptions.CodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final Random random = new Random();

    @Autowired
    ShortUrlRepository repository;



    @Override
    public ShortUrl shortenUrl(String urlOriginal) {
        String code = generateCode(6);



        ShortUrl su = ShortUrl.builder()
                .urlOriginal(urlOriginal)
                .code(code)
                .localDateTime(LocalDateTime.now())
                .clickCount(0L)
                .build();

        return repository.save(su);
    }

    @Override
    @Transactional
    public ShortUrl getAndIncrement(String code) {
        ShortUrl su = repository.findByCode(code).orElseThrow(()-> new CodeNotFoundException(code));
        su.setClickCount(su.getClickCount() + 1);

        return su;
    }


    private String generateCode(int length){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));

        }
        return  sb.toString();
    }
}
