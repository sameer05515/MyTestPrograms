package com.example.urlshortener.repository;

import com.example.urlshortener.entity.AppUser;
import com.example.urlshortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortCode(String shortCode);

    @Query("""
            select u from UrlMapping u
            where u.owner = :owner
              and u.longUrlHash = :longUrlHash
              and (u.expiresAt is null or u.expiresAt > :now)
            order by u.createdAt desc
            """)
    Optional<UrlMapping> findActiveByOwnerAndLongUrlHash(@Param("owner") AppUser owner,
                                                         @Param("longUrlHash") String longUrlHash,
                                                         @Param("now") Instant now);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update UrlMapping u set u.clickCount = u.clickCount + 1 where u.shortCode = :shortCode")
    int incrementClickCount(@Param("shortCode") String shortCode);
}
