//package org.jeka.demowebinar1no_react.services;
//
//import jakarta.transaction.Transactional;
//import org.jeka.demowebinar1no_react.model.Chat;
//import org.jeka.demowebinar1no_react.model.ChatEntry;
//import org.jeka.demowebinar1no_react.repo.ChatRepository;
//import org.springframework.ai.chat.memory.ChatMemory;
//import org.springframework.ai.chat.messages.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class PostgresChatMemory implements ChatMemory {
//
//    @Autowired
//    private ChatRepository chatRepository;
//
//
//    @Override
//    @Transactional
//    public void add(String conversationId, List<Message> messages) {
//        for (Message message : messages) {
//            Chat chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
//            chat.addChatEntry(ChatEntry.toChatEntry(message));
//        }
//    }
//
//    @Override
//    public List<Message> get(String conversationId, int lastN) {
//        Chat chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
//        return chat.getHistory().stream()
//                .map(ChatEntry::toMessage)
//                .limit(lastN)
//                .toList();
//    }
//
//    @Override
//    public void clear(String conversationId) {
//        // not implemented
//    }
//}
