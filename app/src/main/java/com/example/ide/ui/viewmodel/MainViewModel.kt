package com.example.ide.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ide.data.model.*
import com.example.ide.data.repository.AIRepository
import com.example.ide.data.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val aiRepository: AIRepository,
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _currentProject = MutableStateFlow<Project?>(null)
    val currentProject: StateFlow<Project?> = _currentProject.asStateFlow()

    private val _currentFile = MutableStateFlow<CodeFile?>(null)
    val currentFile: StateFlow<CodeFile?> = _currentFile.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _availableModels = MutableStateFlow<List<AIModel>>(emptyList())
    val availableModels: StateFlow<List<AIModel>> = _availableModels.asStateFlow()

    private val _selectedModel = MutableStateFlow<AIModel?>(null)
    val selectedModel: StateFlow<AIModel?> = _selectedModel.asStateFlow()

    private val _apiKeys = MutableStateFlow<Map<AIModelType, String>>(emptyMap())
    val apiKeys: StateFlow<Map<AIModelType, String>> = _apiKeys.asStateFlow()

    // For undo functionality
    private val deletedProjects = mutableMapOf<String, Project>()
    private val deletedFiles = mutableMapOf<String, Pair<CodeFile, String>>() // fileId -> (file, projectName)

    init {
        loadProjects()
        loadAvailableModels()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            _projects.value = fileRepository.getAllProjects()
        }
    }

    private fun loadAvailableModels() {
        _availableModels.value = aiRepository.getAvailableModels()
        _selectedModel.value = _availableModels.value.firstOrNull()
    }

    fun createNewProject(name: String) {
        val project = Project(name = name)
        fileRepository.saveProject(project)
        loadProjects()
        _currentProject.value = project
    }

    fun openProject(project: Project) {
        _currentProject.value = project
        _currentFile.value = project.files.firstOrNull()
    }

    fun createNewFile(name: String, extension: String) {
        val currentProj = _currentProject.value ?: return
        val newFile = fileRepository.createNewFile(name, extension)
        
        currentProj.files.add(newFile)
        fileRepository.saveProject(currentProj)
        _currentFile.value = newFile
        _currentProject.value = currentProj.copy()
    }

    fun updateFileContent(content: String) {
        val currentProj = _currentProject.value ?: return
        val currentFileValue = _currentFile.value ?: return
        
        val updatedFile = currentFileValue.copy(
            content = content,
            isModified = true,
            lastModified = System.currentTimeMillis()
        )
        
        val fileIndex = currentProj.files.indexOfFirst { it.id == currentFileValue.id }
        if (fileIndex >= 0) {
            currentProj.files[fileIndex] = updatedFile
            _currentFile.value = updatedFile
            _currentProject.value = currentProj.copy()
        }
    }

    fun saveCurrentFile() {
        val currentProj = _currentProject.value ?: return
        val currentFileValue = _currentFile.value ?: return
        
        val savedFile = currentFileValue.copy(isModified = false)
        val fileIndex = currentProj.files.indexOfFirst { it.id == currentFileValue.id }
        if (fileIndex >= 0) {
            currentProj.files[fileIndex] = savedFile
            fileRepository.saveProject(currentProj)
            _currentFile.value = savedFile
            _currentProject.value = currentProj.copy()
        }
    }

    fun saveFileToDownloads(fileName: String, content: String, extension: String) {
        viewModelScope.launch {
            val result = fileRepository.saveFileToDownloads(fileName, content, extension)
            result.onSuccess { path ->
                _uiState.value = _uiState.value.copy(
                    message = "File saved to: $path",
                    isLoading = false
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Save code from chat to the current project folder in Downloads
     */
    fun saveChatCodeToProject(fileName: String, codeContent: String, extension: String = "txt") {
        val currentProject = _currentProject.value ?: return
        
        viewModelScope.launch {
            val result = fileRepository.saveFileToProject(
                projectName = currentProject.name,
                fileName = fileName,
                content = codeContent,
                extension = extension
            )
            
            result.onSuccess { path ->
                _uiState.value = _uiState.value.copy(
                    message = "Code saved to project folder: $path",
                    isLoading = false
                )
                
                // Also update the in-memory project file list
                try {
                    val newFile = fileRepository.createNewFile(fileName, extension).copy(
                        content = codeContent,
                        isModified = false
                    )
                    
                    currentProject.files.add(newFile)
                    fileRepository.saveProject(currentProject)
                    loadProjects()
                } catch (e: Exception) {
                    // Handle error silently - file is already saved to disk
                }
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save code to project: ${error.message}",
                    isLoading = false
                )
            }
        }
    }

    fun selectFile(file: CodeFile) {
        _currentFile.value = file
    }

    fun selectModel(model: AIModel) {
        _selectedModel.value = model
    }

    fun setApiKey(modelType: AIModelType, apiKey: String) {
        val currentKeys = _apiKeys.value.toMutableMap()
        currentKeys[modelType] = apiKey
        _apiKeys.value = currentKeys
    }

    fun deleteProject(projectId: String) {
        viewModelScope.launch {
            try {
                // Save project for potential undo
                val projectToDelete = _projects.value.find { it.id == projectId }
                projectToDelete?.let {
                    deletedProjects[projectId] = it
                }
                
                fileRepository.deleteProject(projectId)
                loadProjects()
                _uiState.value = _uiState.value.copy(
                    message = "Project deleted. Swipe to undo.",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete project: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
    
    fun undoDeleteProject(projectId: String) {
        viewModelScope.launch {
            try {
                val projectToRestore = deletedProjects[projectId]
                projectToRestore?.let {
                    fileRepository.saveProject(it)
                    loadProjects()
                    deletedProjects.remove(projectId)
                    _uiState.value = _uiState.value.copy(
                        message = "Project restored",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to restore project: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
    
    fun deleteFileFromProject(projectName: String, fileName: String, extension: String) {
        viewModelScope.launch {
            try {
                // Save file for potential undo
                val currentProj = _currentProject.value
                currentProj?.let {
                    val fileToDelete = it.files.find { file -> 
                        file.name == fileName && file.extension == extension 
                    }
                    fileToDelete?.let { file ->
                        deletedFiles[file.id] = Pair(file, projectName)
                    }
                }
                
                fileRepository.deleteFileFromProject(projectName, fileName, extension)
                _uiState.value = _uiState.value.copy(
                    message = "File deleted. Swipe to undo.",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete file: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
    
    fun undoDeleteFile(fileId: String) {
        viewModelScope.launch {
            try {
                val fileToRestore = deletedFiles[fileId]
                fileToRestore?.let { (file, projectName) ->
                    val result = fileRepository.saveFileToProject(
                        projectName = projectName,
                        fileName = file.name,
                        content = file.content,
                        extension = file.extension
                    )
                    
                    result.onSuccess {
                        // Update project in memory
                        val currentProj = _currentProject.value
                        currentProj?.let {
                            val updatedProject = it.copy()
                            updatedProject.files.add(file)
                            _currentProject.value = updatedProject
                            loadProjects()
                            deletedFiles.remove(fileId)
                            _uiState.value = _uiState.value.copy(
                                message = "File restored",
                                isLoading = false
                            )
                        }
                    }?.onFailure { error ->
                        _uiState.value = _uiState.value.copy(
                            error = "Failed to restore file: ${error.message}",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to restore file: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun sendChatMessage(message: String) {
        val model = _selectedModel.value ?: return
        val apiKey = _apiKeys.value[model.type]
        
        if (apiKey.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(error = "API key required for ${model.name}")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val userMessage = ChatMessage(role = "user", content = message)
            val currentMessages = _chatMessages.value.toMutableList()
            currentMessages.add(userMessage)
            _chatMessages.value = currentMessages

            // Add system message for coding context if we have a current file
            val messages = mutableListOf<ChatMessage>()
            _currentFile.value?.let { file ->
                messages.add(ChatMessage(
                    role = "system", 
                    content = "You are a coding assistant. The user is working on a ${file.language} file named ${file.name}. Current file content:\n\n${file.content}"
                ))
            }
            messages.addAll(currentMessages)

            val result = aiRepository.sendMessage(model.type, apiKey, messages)
            
            result.onSuccess { response ->
                val assistantMessage = ChatMessage(role = "assistant", content = response)
                currentMessages.add(assistantMessage)
                _chatMessages.value = currentMessages
                _uiState.value = _uiState.value.copy(isLoading = false)
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message,
                    isLoading = false
                )
            }
        }
    }

    fun clearChat() {
        _chatMessages.value = emptyList()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)