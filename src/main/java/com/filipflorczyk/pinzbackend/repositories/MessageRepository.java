package com.filipflorczyk.pinzbackend.repositories;

import com.filipflorczyk.pinzbackend.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    Page<Message> findAllByReceiverPlayer_Id(Long id, Pageable pageable);
    Page<Message> findAllBySenderPlayer_Id(Long id, Pageable pageable);
    Page<Message> findAllByReceiverPlayerIdAndSenderPlayerId(Long ReceiverPlayer, Long SenderPlayerId, Pageable pageable);
}
