package com.example.calmcloud.subpage.chatbot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcloud.R
import com.example.calmcloud.api.Message
import com.example.calmcloud.api.OpenAIChatRequest
import com.example.calmcloud.api.OpenAIChatResponse
import com.example.calmcloud.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotActivity : AppCompatActivity() {

    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendButton: Button
    private lateinit var userInputEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        recyclerView = findViewById(R.id.recyclerView)
        sendButton = findViewById(R.id.sendButton)
        userInputEditText = findViewById(R.id.userInputEditText)

        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val userInput = userInputEditText.text.toString()
            if (userInput.isNotBlank()) {
                addMessage(userInput, true)
                sendMessageToChatbot(userInput)
                userInputEditText.text.clear()
            }
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {
        messages.add(ChatMessage(message, isUser))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun sendMessageToChatbot(userInput: String) {
        val chatHistory = messages.map {
            Message(
                role = if (it.isUser) "user" else "assistant",
                content = it.message
            )
        }

        val request = OpenAIChatRequest(
            model = "gpt-3.5-turbo",
            messages = chatHistory + Message(role = "user", content = userInput)
        )

        RetrofitClient.openAiApi.getChatCompletion(request).enqueue(object : Callback<OpenAIChatResponse> {
            override fun onResponse(call: Call<OpenAIChatResponse>, response: Response<OpenAIChatResponse>) {
                if (response.isSuccessful) {
                    val botResponse = response.body()?.choices?.firstOrNull()?.message?.content ?: "No response"
                    addMessage(botResponse, false)
                } else {
                    addMessage("Error: ${response.errorBody()?.string()}", false)
                }
            }

            override fun onFailure(call: Call<OpenAIChatResponse>, t: Throwable) {
                addMessage("Error: ${t.message}", false)
            }
        })
    }
}
