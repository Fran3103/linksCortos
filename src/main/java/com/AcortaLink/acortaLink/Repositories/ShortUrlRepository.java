package com.AcortaLink.acortaLink.Repositories;

import com.AcortaLink.acortaLink.Entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository< ShortUrl, Long> {
    Optional<ShortUrl> findByCode (String code);
}
