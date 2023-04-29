package com.server.talkster.services.chat;

import com.server.talkster.models.GroupMember;
import com.server.talkster.models.User;
import com.server.talkster.repositories.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService
{
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public GroupMemberService(GroupMemberRepository groupMemberRepository) { this.groupMemberRepository = groupMemberRepository; }
    public List<User> findAllGroupMembers(long id) { return groupMemberRepository.findAllGroupMembers(id); }

    public GroupMember save(GroupMember groupMember) { return groupMemberRepository.save(groupMember); }
}
