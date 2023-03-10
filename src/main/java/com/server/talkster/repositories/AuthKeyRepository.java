package com.server.talkster.repositories;

import com.server.talkster.models.AuthKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AuthKeyRepository extends JpaRepository<AuthKey, Long>
{

}
