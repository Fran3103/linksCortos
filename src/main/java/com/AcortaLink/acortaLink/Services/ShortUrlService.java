package com.AcortaLink.acortaLink.Services;

import com.AcortaLink.acortaLink.Entities.ShortUrl;

public interface ShortUrlService {





    ShortUrl shortenUrl  (String originalUrl);

    ShortUrl getAndIncrement(String code);
}
