package com.springboot.chatapp.springboot.repository;

import com.springboot.chatapp.springboot.entity.ConversationThreads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationThreadsRepository extends JpaRepository<ConversationThreads, Integer> {
}
