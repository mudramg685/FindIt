package com.findit.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="contact_messages")
public class ContactMessage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="item_id") private Item item;
    @ManyToOne(optional=false) @JoinColumn(name="sender_id") private AppUser sender;
    @ManyToOne(optional=false) @JoinColumn(name="recipient_id") private AppUser recipient;
    @Column(nullable=false,length=2000) private String message;
    @Column(nullable=false) private Instant createdAt=Instant.now();
    @Column(length=20) private String claimDecision;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="claim_reviewed_by_id") private AppUser claimReviewedBy;
    private Instant claimReviewedAt;
    public Long getId(){return id;} public Item getItem(){return item;} public void setItem(Item v){item=v;}
    public AppUser getSender(){return sender;} public void setSender(AppUser v){sender=v;}
    public AppUser getRecipient(){return recipient;} public void setRecipient(AppUser v){recipient=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public Instant getCreatedAt(){return createdAt;}
    public String getClaimDecision(){return claimDecision;} public void setClaimDecision(String v){claimDecision=v;}
    public AppUser getClaimReviewedBy(){return claimReviewedBy;} public void setClaimReviewedBy(AppUser v){claimReviewedBy=v;}
    public Instant getClaimReviewedAt(){return claimReviewedAt;} public void setClaimReviewedAt(Instant v){claimReviewedAt=v;}
}
