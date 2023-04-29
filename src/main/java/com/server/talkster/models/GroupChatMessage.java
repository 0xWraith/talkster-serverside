package com.server.talkster.models;

import com.server.talkster.utility.Enums.MessageType;
import jakarta.persistence.*;

@Table(name = "group_messages")
@Entity(name = "GroupChatMessage")
public class GroupChatMessage
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_id")
    private long chatID;

    @Column(name = "sender_id")
    private long senderID;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "message_type")
    private MessageType messageType;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "created_at")
    private String messageTimestamp;

    public GroupChatMessage() { }

    public long getId() { return id; }
    public long getChatID() { return chatID; }
    public long getSenderID() { return senderID; }
    public MessageType getMessageType() { return messageType; }
    public String getMessageContent() { return messageContent; }
    public String getMessageTimestamp() { return messageTimestamp; }

    public void setId(long id) { this.id = id; }
    public void setChatID(long chatID) { this.chatID = chatID; }
    public void setSenderID(long senderID) { this.senderID = senderID; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }
    public void setMessageContent(String messageContent) { this.messageContent = messageContent; }
    public void setMessageTimestamp(String messageTimestamp) { this.messageTimestamp = messageTimestamp; }
}
