package com.filipflorczyk.pinzbackend.services.interfaces;

import com.filipflorczyk.pinzbackend.dtos.MessageDto.MessageDto;
import com.filipflorczyk.pinzbackend.dtos.MessageDto.NewMessageDto;
import com.filipflorczyk.pinzbackend.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService  extends BaseService<Message, MessageDto> {

    Page<MessageDto> getAllMyReceivedMessages(Pageable pageable);
    Page<MessageDto> getAllMySentMessages(Pageable pageable);
    Page<MessageDto> getAllMyReceivedMessagesFromPlayer(Long id, Pageable pageable);
    void sendMessage(NewMessageDto newMessageDto);
    void deleteMessage(Long id);

}
