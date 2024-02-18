package com.springboot.chatapp.springboot.service_impl;

import com.springboot.chatapp.springboot.entity.ConversationThreads;
import com.springboot.chatapp.springboot.entity.Message;
import com.springboot.chatapp.springboot.exception.RESTException;
import com.springboot.chatapp.springboot.model.PrivateChatResponse;
import com.springboot.chatapp.springboot.repository.ConversationThreadsRepository;
import com.springboot.chatapp.springboot.repository.MessageRepository;
import com.springboot.chatapp.springboot.service.MessageService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationThreadsRepository conversationThreadsRepository;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ConversationThreadsRepository conversationThreadsRepository
    ) {
        this.messageRepository = messageRepository;
        this.conversationThreadsRepository = conversationThreadsRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        ConversationThreads conversationThreads = new ConversationThreads();
        conversationThreads.setLatestMessage(message.getContent());
        conversationThreads.setLastMessageTime(message.getTimestamp());
        conversationThreads.setSenderId(message.getSenderId());
        conversationThreads.setSenderName(message.getSenderName());
        conversationThreads.setReceiverId(message.getReceiverId());
        conversationThreads.setReceiverName(message.getReceiverName());
        conversationThreadsRepository.save(conversationThreads);
        return messageRepository.save(message);
    }

    @Override
    public PrivateChatResponse getPrivateChatMessages(
            int pageNo,
            int pageSize,
            int senderId,
            int receiverId
    ) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "timestamp"));

        Page<Message> messagesPage = messageRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get("senderId"), senderId),
                                    criteriaBuilder.equal(root.get("receiverId"), receiverId)
                            ),
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get("senderId"), receiverId),
                                    criteriaBuilder.equal(root.get("receiverId"), senderId)
                            )
                    )
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageRequest);

        PrivateChatResponse privateChatResponse = new PrivateChatResponse();
        privateChatResponse.setMessages(messagesPage.getContent());
        privateChatResponse.setLastPage(messagesPage.isLast());
        privateChatResponse.setFirstPage(messagesPage.isFirst());
        privateChatResponse.setTotalPages(messagesPage.getTotalPages());
        privateChatResponse.setPageNo(messagesPage.getNumber());
        privateChatResponse.setPageSize(messagesPage.getSize());
        return privateChatResponse;
    }

    @Override
    public Message updateMessage(
            int id,
            String content,
            String editedTimestamp
    ) throws RESTException {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RESTException("Message not found"));
        message.setContent(content);
        message.setEditedTimestamp(editedTimestamp);
        return messageRepository.save(message);
    }

    @Override
    public String deleteMessage(int id) throws RESTException {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RESTException("Message not found"));
        messageRepository.delete(message);
        return "Message deleted successfully";
    }
}
