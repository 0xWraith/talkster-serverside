package com.server.talkster.models;

import java.time.OffsetDateTime;
import java.util.List;

import com.server.talkster.Chat;
import com.server.talkster.utility.Enums.EChatType;
import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class PrivateChat extends Chat
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "owner_id")
    private long ownerID;

    @Column(name = "receiver_id")
    private long receiverID;

    @Column(name = "mute_time")
    private long muteTime;

    @Column(name = "chat_type")
    private EChatType type;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_blocking")
    private boolean isBlocking;

    @Transient
    private String receiverLastname;

    @Transient
    private String receiverFirstname;

    @Transient
    protected List<PrivateChatMessage> messages;

    public PrivateChat()
    {
        type = EChatType.PRIVATE_CHAT;
        updatedAt = OffsetDateTime.now().toString();
    }

    public PrivateChat(long ID, long ownerID, long receiverID, long muteTime, boolean isBlocked, boolean isBlocking, EChatType type, String receiverLastname, String receiverFirstname)
    {
        this.ID = ID;
        this.type = type;
        this.ownerID = ownerID;
        this.muteTime = muteTime;
        this.isBlocked = isBlocked;
        this.isBlocking = isBlocking;
        this.receiverID = receiverID;
        this.receiverLastname = receiverLastname;
        this.receiverFirstname = receiverFirstname;
    }

    public boolean isMuted()
    {
        if(muteTime == -1)
            return true;

        return muteTime > System.currentTimeMillis() / 1000L;
    }

    public long getID() { return ID; }
    public EChatType getType() { return type; }
    public long getOwnerID() { return ownerID; }
    public long getMuteTime() { return muteTime; }
    public long getReceiverID() { return receiverID; }
    public String getUpdatedAt() { return updatedAt; }
    public String getReceiverLastname() { return receiverLastname; }
    public String getReceiverFirstname() { return receiverFirstname; }
    public List<PrivateChatMessage> getMessages() { return messages; }
    public boolean getIsBlocked() { return isBlocked; }
    public boolean getIsBlocking() { return isBlocking; }


    public void setID(long ID) { this.ID = ID; }
    public void setType(EChatType type) { this.type = type; }
    public void setOwnerID(long ownerID) { this.ownerID = ownerID; }
    public void setMuteTime(long muteTime) { this.muteTime = muteTime; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public void setReceiverID(long receiverID) { this.receiverID = receiverID; }
    public void setMessages(List<PrivateChatMessage> messages) { this.messages = messages; }
    public void setReceiverLastname(String receiverLastname) { this.receiverLastname = receiverLastname; }
    public void setReceiverFirstname(String receiverFirstname) { this.receiverFirstname = receiverFirstname; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }
    public void setBlocking(boolean blocked) { isBlocking = blocked; }


    @Override
    public String toString() {
        return "Chat{" +
                "ID=" + ID +
                ", ownerID=" + ownerID +
                ", receiverID=" + receiverID +
                ", muteTime=" + muteTime +
                ", type=" + type +
                ", updatedAt='" + updatedAt + '\'' +
                ", receiverLastname='" + receiverLastname + '\'' +
                ", receiverFirstname='" + receiverFirstname + '\'' +
                ", messages=" + messages +
                '}';
    }
}
