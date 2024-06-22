package com.example.calmcloud.api

data class OpenAIChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class OpenAIChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)