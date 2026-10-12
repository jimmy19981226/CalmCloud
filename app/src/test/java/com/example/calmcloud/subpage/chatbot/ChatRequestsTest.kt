package com.example.calmcloud.subpage.chatbot

import org.junit.Assert.assertEquals
import org.junit.Test

class ChatRequestsTest {
    @Test
    fun newestUserMessageIsIncludedExactlyOnce() {
        val request = buildChatRequest(listOf(ChatMessage("How can I unwind?", true)), "test-model")
        assertEquals(1, request.messages.size)
        assertEquals("user", request.messages.single().role)
        assertEquals("How can I unwind?", request.messages.single().content)
        assertEquals("test-model", request.model)
    }

    @Test
    fun conversationOrderAndRolesArePreserved() {
        val request = buildChatRequest(listOf(
            ChatMessage("Hello", true), ChatMessage("Welcome", false), ChatMessage("Thanks", true)
        ), "test-model")
        assertEquals(listOf("user", "assistant", "user"), request.messages.map { it.role })
        assertEquals(listOf("Hello", "Welcome", "Thanks"), request.messages.map { it.content })
    }

    @Test(expected = IllegalArgumentException::class)
    fun emptyConversationIsRejected() {
        buildChatRequest(emptyList(), "test-model")
    }
}
