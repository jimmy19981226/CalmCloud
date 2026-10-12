package com.example.calmcloud.subpage.chatbot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calmcloud.R
import com.example.calmcloud.BuildConfig
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
    private var activeCall: Call<OpenAIChatResponse>? = null

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
            if (!RetrofitClient.isChatConfigured) {
                Toast.makeText(this, R.string.chat_not_configured, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val userInput = userInputEditText.text.toString().trim()
            if (userInput.isNotBlank()) {
                addMessage(userInput, true)
                sendMessageToChatbot()
                userInputEditText.text.clear()
            }
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {
        messages.add(ChatMessage(message, isUser))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun sendMessageToChatbot() {
        val request = buildChatRequest(messages, BuildConfig.CHAT_MODEL)
        sendButton.isEnabled = false
        val call = RetrofitClient.openAiApi.getChatCompletion(request)
        activeCall = call
        call.enqueue(object : Callback<OpenAIChatResponse> {
            override fun onResponse(call: Call<OpenAIChatResponse>, response: Response<OpenAIChatResponse>) {
                if (isFinishing || isDestroyed) return
                activeCall = null
                sendButton.isEnabled = true
                val botResponse = response.body()?.choices?.firstOrNull()?.message?.content
                if (response.isSuccessful && !botResponse.isNullOrBlank()) {
                    addMessage(botResponse, false)
                } else {
                    response.errorBody()?.close()
                    Toast.makeText(this@ChatBotActivity, R.string.chat_response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<OpenAIChatResponse>, t: Throwable) {
                if (call.isCanceled || isFinishing || isDestroyed) return
                activeCall = null
                sendButton.isEnabled = true
                Toast.makeText(this@ChatBotActivity, R.string.chat_network_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        activeCall?.cancel()
        activeCall = null
        super.onDestroy()
    }
}
