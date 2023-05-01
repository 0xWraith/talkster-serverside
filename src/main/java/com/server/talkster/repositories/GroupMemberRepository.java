package com.server.talkster.repositories;

import com.server.talkster.models.GroupMember;
import com.server.talkster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>
{
    @Query("SELECT new User(u.id, u.firstname, u.lastname, u.username, u.imageID) FROM GroupMember AS gm JOIN User AS u on u.id = gm.userId WHERE gm.groupId = :groupId")
    List<User> findAllGroupMembers(long groupId);
}
