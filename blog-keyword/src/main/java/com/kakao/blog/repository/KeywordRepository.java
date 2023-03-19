package com.kakao.blog.repository;

import com.kakao.blog.entity.Keyword;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update Keyword k set k.count = k.count+1 where k.id=:keywordId", nativeQuery = true)
    void increaseKeywordCount(@Param("keywordId") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="500")})
    Optional<Keyword> findByKeyword(String keyword);

}
