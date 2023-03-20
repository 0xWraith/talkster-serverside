package com.server.talkster.models;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "owner_id")
    private long ownerID;

    @Column(name = "receiver_id")
    private long receiverID;

    @Column(name = "updated_at")
    private String updatedAt;

    @Transient
    private String receiverLastname;

    @Transient
    private String receiverFirstname;

    @Transient
    private List<Message> messages;

    public Chat() { }

    public Chat(long ID, long ownerID, long receiverID, String receiverLastname, String receiverFirstname)
    {
        this.ID = ID;
        this.ownerID = ownerID;
        this.receiverID = receiverID;
        this.receiverLastname = receiverLastname;
        this.receiverFirstname = receiverFirstname;
    }


    public long getID() { return ID; }
    public long getOwnerID() { return ownerID; }
    public long getReceiverID() { return receiverID; }
    public String getUpdatedAt() { return updatedAt; }
    public List<Message> getMessages() { return messages; }
    public String getReceiverLastname() { return receiverLastname; }
    public String getReceiverFirstname() { return receiverFirstname; }

    public void setID(long ID) { this.ID = ID; }
    public void setOwnerID(long ownerID) { this.ownerID = ownerID; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public void setReceiverID(long receiverID) { this.receiverID = receiverID; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public void setReceiverLastname(String receiverLastname) { this.receiverLastname = receiverLastname; }
    public void setReceiverFirstname(String receiverFirstname) { this.receiverFirstname = receiverFirstname; }

    @Override
    public String toString() {
        return "Chat{" +
                "ID=" + ID +
                ", ownerID=" + ownerID +
                ", receiverID=" + receiverID +
                ", receiverLastname='" + receiverLastname + '\'' +
                ", receiverFirstname='" + receiverFirstname + '\'' +
                ", messages=" + messages +
                ", updatedAt='" + updatedAt +
                '}';
    }
}