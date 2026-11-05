package com.findit.repo;
import com.findit.model.Item; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface ItemRepository extends JpaRepository<Item,Long> {
 @Query("select i from Item i join fetch i.user left join fetch i.claimedBy where (:q is null or lower(i.name) like lower(concat('%',:q,'%')) or lower(i.description) like lower(concat('%',:q,'%')) or lower(i.location) like lower(concat('%',:q,'%'))) and (:category is null or i.category=:category) and (:type is null or i.type=:type) and (:location is null or lower(i.location) like lower(concat('%',:location,'%'))) and (:status is null or i.status=:status) order by i.createdAt desc")
 List<Item> search(@Param("q") String q,@Param("category") String category,@Param("type") String type,@Param("location") String location,@Param("status") String status);
 @Query("select i from Item i join fetch i.user left join fetch i.claimedBy where i.id=:id") Optional<Item> withPeopleById(@Param("id") Long id);
 @Query("select i from Item i join fetch i.user left join fetch i.claimedBy where i.user.id=:uid order by i.createdAt desc") List<Item> byOwner(@Param("uid") Long uid);
 @Query("select i from Item i join fetch i.user left join fetch i.claimedBy order by i.createdAt desc") List<Item> allWithOwner();
}
