package com.example.ide.data.api

import com.example.ide.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface OpenAIApiService {
    @POST("v1/chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun chatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}

interface ClaudeApiService {
    @POST("v1/messages")
    @Headers("Content-Type: application/json")
    suspend fun createMessage(
        @Header("x-api-key") apiKey: String,
        @Header("anthropic-version") version: String = "2023-06-01",
        @Body request: ClaudeRequest
    ): Response<ClaudeResponse>
}

interface GeminiApiService {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}

// OpenRouter API service
interface OpenRouterApiService {
    @POST("v1/chat/completions")
    @Headers("Content-Type: application/json")
    suspend fun chatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: OpenRouterRequest
    ): Response<ChatResponse>
}

// Claude-specific models
data class ClaudeRequest(
    val model: String,
    val maxTokens: Int,
    val messages: List<ClaudeMessage>,
    val temperature: Double = 0.7
)

data class ClaudeMessage(
    val role: String,
    val content: String
)

data class ClaudeResponse(
    val id: String,
    val type: String,
    val role: String,
    val content: List<ClaudeContent>,
    val model: String,
    val stopReason: String?,
    val usage: ClaudeUsage
)

data class ClaudeContent(
    val type: String,
    val text: String
)

data class ClaudeUsage(
    val inputTokens: Int,
    val outputTokens: Int
)

// Gemini-specific models
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val generationConfig: GeminiGenerationConfig? = null
)

data class GeminiContent(
    val parts: List<GeminiPart>
)

data class GeminiPart(
    val text: String
)

data class GeminiGenerationConfig(
    val temperature: Double = 0.7,
    val maxOutputTokens: Int = 4096
)

data class GeminiResponse(
    val candidates: List<GeminiCandidate>?,
    val usageMetadata: GeminiUsageMetadata?
)

data class GeminiCandidate(
    val content: GeminiContent,
    val finishReason: String?
)

data class GeminiUsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int
)

// OpenRouter-specific models
data class OpenRouterRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val maxTokens: Int = 4096,
    val temperature: Double = 0.7,
    val stream: Boolean = false
)
