package com.server.talkster.models;

import com.server.talkster.utility.Enums.MessageType;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class PrivateChatMessage
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id")
    private long chatID;

    @Column(name = "sender_id")
    private long senderID;

    @Column(name = "receiver_id")
    private long receiverID;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "message_type")
    private MessageType messageType;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "created_at")
    private String messageTimestamp;

    public PrivateChatMessage() { }

    public long getId() { return id; }
    public long getChatID() { return chatID; }
    public long getSenderID() { return senderID; }
    public long getReceiverID() { return receiverID; }
    public MessageType getMessageType() { return messageType; }
    public String getMessageContent() { return messageContent; }
    public String getMessageTimestamp() { return messageTimestamp; }

    public void setId(long id) { this.id = id; }
    public void setChatID(long chatID) { this.chatID = chatID; }
    public void setSenderID(long senderID) { this.senderID = senderID; }
    public void setReceiverID(long receiverID) { this.receiverID = receiverID; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
    public void setMessageTimestamp(String messageTimestamp) { this.messageTimestamp = messageTimestamp; }
}
