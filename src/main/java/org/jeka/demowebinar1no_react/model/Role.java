package org.jeka.demowebinar1no_react.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER("user"), ASSISTANT("assistant"), SYSTEM("system");

    private final String role;

    public static Role getRole(String roleName) {
        return Arrays.stream(Role.values()).filter(role -> role.role.equals(roleName)).findFirst().orElseThrow();
    }

}
