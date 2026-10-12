package com.example.calmcloud.subpage.chatbot

import com.example.calmcloud.api.Message
import com.example.calmcloud.api.OpenAIChatRequest

/** Build from the conversation after the newest user message is appended. */
internal fun buildChatRequest(messages: List<ChatMessage>, model: String): OpenAIChatRequest {
    require(messages.isNotEmpty()) { "A conversation must contain a message" }
    return OpenAIChatRequest(
        model = model,
        messages = messages.map {
            Message(role = if (it.isUser) "user" else "assistant", content = it.message)
        }
    )
}
