package com.server.talkster.models;

import jakarta.persistence.*;

@Table(name = "groups")
@Entity(name = "GroupMember")

public class GroupMember
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "group_id")
    private long groupId;

    public GroupMember() { }

    public GroupMember(long userId, long groupId)
    {
        this.userId = userId;
        this.groupId = groupId;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public long getGroupId() { return groupId; }

    public void setId(long id) { this.id = id; }
    public void setUserId(long userId) { this.userId = userId; }
    public void setGroupId(long groupId) { this.groupId = groupId; }
}
