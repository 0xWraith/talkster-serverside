package com.server.talkster;

import com.server.talkster.utility.Enums.EChatType;
import jakarta.persistence.Transient;

public abstract class Chat
{
    @Transient
    protected EChatType type;

    public EChatType getType() { return type; }
    public void setType(EChatType type) { this.type = type; }
}
