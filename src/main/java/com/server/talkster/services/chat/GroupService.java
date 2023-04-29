package com.server.talkster.services.chat;

import com.server.talkster.models.GroupChat;
import com.server.talkster.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService
{
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) { this.groupRepository = groupRepository; }

    public GroupChat findGroupByID(long id) { return groupRepository.findById(id); }
    public List<GroupChat> findAllGroupsByUserId(long id) { return groupRepository.findAllGroupsByUserId(id); }

    public GroupChat save(GroupChat group) { return groupRepository.save(group); }
}
