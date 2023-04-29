package com.server.talkster.dto;

import com.server.talkster.models.User;

import java.util.List;


public class CreateGroupDTO
{
    private final long id;
    private final String groupname;
    private final List<Long> members;

    public CreateGroupDTO(long id, String groupname, List<Long> members)
    {
        this.id = id;
        this.members = members;
        this.groupname = groupname;
    }

    public long getId() { return id; }
    public String getgroupname() { return groupname; }
    public List<Long> getMembers() { return members; }

    @Override
    public String toString()
    {
        return "CreateGroupDTO{" +
                "groupName='" + groupname + '\'' +
                ", members=" + members +
                '}';
    }
}