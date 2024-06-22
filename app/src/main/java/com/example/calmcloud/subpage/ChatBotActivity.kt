package com.example.calmcloud.subpage

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calmcloud.R
import com.example.calmcloud.api.OpenAIChatRequest
import com.example.calmcloud.api.OpenAIChatResponse
import com.example.calmcloud.api.Message
import com.example.calmcloud.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotActivity : AppCompatActivity() {

    private lateinit var conversationTextView: TextView
    private lateinit var userInputEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        conversationTextView = findViewById(R.id.conversationTextView)
        userInputEditText = findViewById(R.id.userInputEditText)
        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val userInput = userInputEditText.text.toString()
            if (userInput.isNotBlank()) {
                appendToConversation("User: $userInput\n")
                sendMessageToChatbot(userInput)
                userInputEditText.text.clear()
            }
        }
    }

    private fun appendToConversation(text: String) {
        conversationTextView.append(text)
    }

    private fun sendMessageToChatbot(message: String) {
        val request = OpenAIChatRequest(
            messages = listOf(
                Message(role = "user", content = message)
            )
        )

        RetrofitClient.openAiApi.getChatCompletion(request).enqueue(object : Callback<OpenAIChatResponse> {
            override fun onResponse(call: Call<OpenAIChatResponse>, response: Response<OpenAIChatResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.choices?.firstOrNull()?.message?.content?.trim() ?: "No response"
                    appendToConversation("Bot: $reply\n")
                } else {
                    appendToConversation("Error: ${response.code()} - ${response.message()}\n")
                    Log.e("ChatBotActivity", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<OpenAIChatResponse>, t: Throwable) {
                appendToConversation("Error: ${t.message}\n")
                Log.e("ChatBotActivity", "Network error: ", t)
            }
        })
    }
}