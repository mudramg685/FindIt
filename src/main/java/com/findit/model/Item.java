package com.findit.model;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="items")
public class Item {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private String category;
    @Column(nullable=false,length=2000) private String description;
    @Column(nullable=false) private String type;
    @Column(nullable=false) private String location;
    @Column(nullable=false) private LocalDate itemDate;
    private String imageFilename;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id") private AppUser user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="claimed_by_id") private AppUser claimedBy;
    private Instant claimedAt;
    @Column(nullable=false) private String status="OPEN";
    @Column(nullable=false) private Instant createdAt=Instant.now();
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getType(){return type;} public void setType(String v){type=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public LocalDate getItemDate(){return itemDate;} public void setItemDate(LocalDate v){itemDate=v;}
    public String getImageFilename(){return imageFilename;} public void setImageFilename(String v){imageFilename=v;}
    public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;}
    public AppUser getClaimedBy(){return claimedBy;} public void setClaimedBy(AppUser v){claimedBy=v;}
    public Instant getClaimedAt(){return claimedAt;} public void setClaimedAt(Instant v){claimedAt=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getCreatedAt(){return createdAt;}
}
