package com.server.talkster.models;

import com.server.talkster.Chat;
import com.server.talkster.utility.Enums.EChatType;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "Group")
@Table(name = "group_chats")
public class GroupChat extends Chat
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String groupName;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Transient
    protected List<GroupChatMessage> messages;

    @Transient
    private List<User> groupMembers;

    public GroupChat()
    {
        type = EChatType.GROUP_CHAT;
        updatedAt = OffsetDateTime.now();
    }

    public GroupChat(long id, String groupName, OffsetDateTime updatedAt)
    {
        this.id = id;
        this.groupName = groupName;
        this.groupMembers = null;
        this.updatedAt = updatedAt;
        this.type = EChatType.GROUP_CHAT;
    }

    public long getId() { return id; }
    public String getGroupName() { return groupName; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public List<User> getGroupMembers() { return groupMembers; }
    public void setMessages(List<GroupChatMessage> messages) { this.messages = messages; }

    public void setId(long id) { this.id = id; }
    public List<GroupChatMessage> getMessages() { return messages; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setGroupMembers(List<User> groupMembers) { this.groupMembers = groupMembers; }

    @Override
    public String toString()
    {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", groupMembers=" + groupMembers +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
