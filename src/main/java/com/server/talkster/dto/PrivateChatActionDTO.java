package com.server.talkster.dto;

import com.server.talkster.utility.Enums.EPrivateChatAction;

public class PrivateChatActionDTO
{
    private long muteTime;
    private long ownerChatID;
    private long ownerUserID;
    private long receiverChatID;
    private long receiverUserID;
    private boolean actionForBoth;
    private EPrivateChatAction action;

    public PrivateChatActionDTO() { }

    public long getMuteTime() { return muteTime; }
    public long getOwnerChatID() { return ownerChatID; }
    public long getOwnerUserID() { return ownerUserID; }
    public EPrivateChatAction getAction() { return action; }
    public long getReceiverChatID() { return receiverChatID; }
    public long getReceiverUserID() { return receiverUserID; }
    public boolean getActionForBoth() { return actionForBoth; }

    public void setMuteTime(long muteTime) { this.muteTime = muteTime; }
    public void setAction(EPrivateChatAction action) { this.action = action; }
    public void setOwnerChatID(long ownerChatID) { this.ownerChatID = ownerChatID; }
    public void setOwnerUserID(long ownerUserID) { this.ownerUserID = ownerUserID; }
    public void setReceiverChatID(long receiverChatID) { this.receiverChatID = receiverChatID; }
    public void setReceiverUserID(long receiverUserID) { this.receiverUserID = receiverUserID; }
    public void setActionForBoth(boolean actionForBoth) { this.actionForBoth = actionForBoth; }
}
