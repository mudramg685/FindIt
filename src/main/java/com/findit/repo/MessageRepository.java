package com.findit.repo;
import com.findit.model.ContactMessage; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface MessageRepository extends JpaRepository<ContactMessage,Long> {
 @Query("select m from ContactMessage m join fetch m.item left join fetch m.item.claimedBy join fetch m.sender join fetch m.recipient left join fetch m.claimReviewedBy where m.recipient.id=:uid or m.sender.id=:uid order by m.createdAt desc") List<ContactMessage> forUser(@Param("uid") Long uid);
 @Query("select m from ContactMessage m join fetch m.item left join fetch m.item.claimedBy join fetch m.sender join fetch m.recipient left join fetch m.claimReviewedBy where m.id=:id") Optional<ContactMessage> withDetails(@Param("id") Long id);
 @Query("select m from ContactMessage m join fetch m.item left join fetch m.item.claimedBy join fetch m.sender join fetch m.recipient left join fetch m.claimReviewedBy where m.message like 'CLAIM REQUEST:%' order by m.createdAt desc") List<ContactMessage> allClaimRequests();
 @Query("select (count(m)>0) from ContactMessage m where m.item.id=:itemId and m.message like 'CLAIM REQUEST:%' and (m.claimDecision is null or m.claimDecision='PENDING') and m.item.status='OPEN'") boolean hasPendingClaim(@Param("itemId") Long itemId);
 @Query("select (count(m)>0) from ContactMessage m where m.item.id=:itemId and m.sender.id=:senderId and m.message like 'CLAIM REQUEST:%' and (m.claimDecision is null or m.claimDecision<>'REJECTED')") boolean hasClaimFromSender(@Param("itemId") Long itemId,@Param("senderId") Long senderId);
 boolean existsByItem_IdAndSender_IdAndMessageStartingWith(Long itemId,Long senderId,String messageStart);
 @Modifying @Query("delete from ContactMessage m where m.item.id=:itemId") void deleteForItem(@Param("itemId") Long itemId);
}
