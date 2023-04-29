package com.server.talkster.repositories;

import com.server.talkster.models.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupChat, Long>
{
    GroupChat findById(long id);

    @Query("SELECT new Group(g.id, g.groupName, g.updatedAt) FROM GroupMember AS gm JOIN Group AS g ON gm.groupId = g.id WHERE gm.userId = :id")
    List<GroupChat> findAllGroupsByUserId(long id);
}
