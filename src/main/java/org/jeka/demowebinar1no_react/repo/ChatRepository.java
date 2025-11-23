package org.jeka.demowebinar1no_react.repo;

import org.jeka.demowebinar1no_react.model.Chat;
import org.jeka.demowebinar1no_react.model.ChatEntry;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatMemoryRepository {

    @Override
    default List<String> findConversationIds() {
        return findAll().stream()
                .map(Chat::getId)
                .map(String::valueOf)
                .toList();
    }

    @Override
    default List<Message> findByConversationId(String conversationId) {
//        if(conversationId == ChatMemory.DEFAULT_CONVERSATION_ID) return new ArrayList<>();
        Chat chat = findById(Long.valueOf(conversationId)).orElseThrow();
        return chat.getHistory().stream()
                .map(ChatEntry::toMessage).toList();
    }

    @Override
    default void saveAll(String conversationId, List<Message> messages) {
        Chat chat = findById(Long.valueOf(conversationId)).orElseThrow();
        messages.stream().map(ChatEntry::toChatEntry).forEach(chat::addChatEntry);
        save(chat);
    }

    @Override
    default void deleteByConversationId(String conversationId) {
        // not implemented
    }
}
