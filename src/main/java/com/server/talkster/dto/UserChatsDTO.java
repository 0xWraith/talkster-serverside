package com.server.talkster.dto;

import com.server.talkster.models.GroupChat;
import com.server.talkster.models.PrivateChat;

import java.util.ArrayList;

public class UserChatsDTO
{
    private ArrayList<GroupChat> groupChats;
    private ArrayList<PrivateChat> privateChats;

    public UserChatsDTO() {}
    public UserChatsDTO(ArrayList<GroupChat> groupChats, ArrayList<PrivateChat> privateChats)
    {
        this.groupChats = groupChats;
        this.privateChats = privateChats;
    }

    public ArrayList<GroupChat> getGroupChats() { return groupChats; }
    public ArrayList<PrivateChat> getPrivateChats() { return privateChats; }

    public void setGroupChats(ArrayList<GroupChat> groupChats) { this.groupChats = groupChats; }
    public void setPrivateChats(ArrayList<PrivateChat> privateChats) { this.privateChats = privateChats; }

    @Override
    public String toString()
    {
        return "UserChatsDTO{" +
                "groupChats=" + groupChats +
                ", privateChats=" + privateChats +
                '}';
    }
}
