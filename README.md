# 📱 Mobile IDE - Professional Android Code Editor with AI Assistance

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)

A powerful, feature-rich Android IDE that transforms your mobile device into a professional coding environment with integrated AI assistance from leading models.

<p align="center">
  <img src="https://user-images.githubusercontent.com/placeholder-mobile-ide-screenshot.png" width="200" alt="Mobile IDE Screenshot"/>
  <img src="https://user-images.githubusercontent.com/placeholder-ai-chat-screenshot.png" width="200" alt="AI Chat Screenshot"/>
  <img src="https://user-images.githubusercontent.com/placeholder-code-editor-screenshot.png" width="200" alt="Code Editor Screenshot"/>
</p>

## 🌟 Key Features

### 🚀 Professional IDE Capabilities
- **Multi-language Support**: 20+ programming languages including HTML, CSS, JS, TS, Python, Java, Kotlin, C/C++, C#, PHP, Ruby, Go, Rust, Swift, SQL, JSON, XML, YAML, Markdown
- **Project Management**: Create, organize, and manage multiple coding projects
- **Smart File System**: Automatic project folder organization in Downloads/IDEProjects
- **Code Templates**: Auto-generated templates for rapid development start
- **Export Functionality**: Save files directly to device storage

### 🤖 Advanced AI Integration
Connect with the world's most powerful AI models for real-time coding assistance:

#### 🟢 OpenAI
- **GPT-4** - Most capable model for complex coding tasks
- **GPT-3.5 Turbo** - Fast and efficient for quick assistance

#### 🟣 Anthropic Claude
- **Claude 3 Opus** - Most powerful for intricate programming challenges
- **Claude 3 Sonnet** - Balanced performance and speed
- **Claude 3 Haiku** - Lightning-fast for simple queries

#### 🔵 Google Gemini
- **Gemini Pro** - Advanced AI for comprehensive coding help
- **Gemini 1.5 Flash** - Efficient for rapid responses

#### 🔶 Additional Models
- Cohere Command
- Mistral Large & Medium
- Meta Llama 2 70B
- Code Llama 34B (Specialized for coding)
- **OpenRouter Support** - Automatic model routing
- **Chinese Free Models**: Lingma, Qwen, Longcat AI, DeepSeek, Z, Kimi

### 💬 Intelligent AI Chat
- **Context-Aware Assistance**: AI understands your current file and project context
- **Code Snippet Management**: Save AI-generated code directly to project folders
- **Smart Language Detection**: Automatically assigns correct file extensions
- **Real-time Conversation**: Interactive chat with multiple AI models
- **Error Handling**: Robust error management and user feedback

### 🎨 Modern Mobile UI
- **Material Design 3**: Clean, intuitive interface following latest design guidelines
- **Responsive Layout**: Optimized for all screen sizes and orientations
- **Dark/Light Themes**: Automatic theme switching based on system preferences
- **Touch-Optimized**: Controls designed specifically for mobile interaction
- **Tabbed Navigation**: Seamless switching between Projects, Editor, AI Chat, and Settings

## 📸 Screenshots

<div style="display: flex; justify-content: space-around;">
  <img src="https://user-images.githubusercontent.com/placeholder-projects-screen.png" width="200" alt="Projects Screen"/>
  <img src="https://user-images.githubusercontent.com/placeholder-editor-screen.png" width="200" alt="Code Editor"/>
  <img src="https://user-images.githubusercontent.com/placeholder-chat-screen.png" width="200" alt="AI Chat Interface"/>
  <img src="https://user-images.githubusercontent.com/placeholder-settings-screen.png" width="200" alt="Settings Panel"/>
</div>

## ⚡ Quick Start

### 📋 Prerequisites
- Android device running Android 7.0 (API level 24) or higher
- Android Studio for development (optional)
- API keys for desired AI models

### 🛠️ Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/mobile-ide.git
   ```
2. Open in Android Studio
3. Sync Gradle dependencies
4. Build and run on your Android device or emulator

### 🔑 API Key Setup
To unlock AI features, configure API keys in the Settings panel:

#### OpenAI
1. Visit [OpenAI API Keys](https://platform.openai.com/api-keys)
2. Generate a new API key
3. Add to Settings > API Keys > OpenAI

#### Anthropic Claude
1. Visit [Anthropic Console](https://console.anthropic.com/)
2. Create and copy your API key
3. Add to Settings > API Keys > Anthropic

#### Google Gemini
1. Visit [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Create an API key
3. Add to Settings > API Keys > Google

## 🎯 Usage Guide

### 📁 Project Management
1. Navigate to the **Projects** tab
2. Tap the **+** button to create a new project
3. Enter a descriptive project name
4. Files are automatically organized in `Downloads/IDEProjects/[ProjectName]`

### ✏️ Code Editing
1. Open a project in the **Editor** tab
2. Create new files with the **+** toolbar icon
3. Select from 20+ supported file types
4. Enjoy syntax-aware editing experience

### 🤖 AI Assistance
1. Switch to the **AI Chat** tab
2. Select your preferred model in **Settings**
3. Enter your API key for the chosen provider
4. Ask coding questions or request help with current file
5. Save useful code snippets with one tap

### 💾 File Operations
- **Auto-save**: Files automatically saved to project folders
- **Export**: Download button saves to Downloads folder
- **Run HTML**: Direct browser preview for web files
- **Delete/Undo**: Comprehensive file management with undo capability

## 🏗️ Technical Architecture

### 🧰 Built With
- **[Kotlin](https://kotlinlang.org)** - Modern, concise programming language
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern toolkit for native UI
- **[Material Design 3](https://m3.material.io)** - Latest design system from Google
- **[Retrofit](https://square.github.io/retrofit/)** - Type-safe HTTP client
- **[Gson](https://github.com/google/gson)** - JSON serialization/deserialization
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
- **[ViewModel & StateFlow](https://developer.android.com/topic/libraries/architecture/viewmodel)** - Modern state management
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)** - Data storage solution

### 📁 Project Structure
```
app/src/main/java/com/example/ide/
├── data/
│   ├── api/          # API service interfaces and models
│   ├── model/        # Data models and entities
│   └── repository/   # Data access and business logic
├── ui/
│   ├── screen/       # Jetpack Compose UI screens
│   ├── viewmodel/    # ViewModels for UI state management
│   └── theme/        # Material Design 3 theming
└── di/               # Dependency injection setup
```

## 🔐 Permissions Required
- **INTERNET** - For AI API communications
- **WRITE_EXTERNAL_STORAGE** - For saving projects to Downloads folder
- **READ_EXTERNAL_STORAGE** - For accessing project files

## 🛣️ Roadmap & Future Features
- [ ] **Syntax Highlighting** - Language-aware code coloring
- [ ] **IntelliSense** - Code completion and suggestions
- [ ] **Git Integration** - Version control within the app
- [ ] **Plugin System** - Extend functionality with plugins
- [ ] **Collaborative Editing** - Real-time collaborative coding
- [ ] **Advanced Debugging** - Integrated debugging tools
- [ ] **Terminal Emulator** - Built-in command line interface
- [ ] **Cloud Sync** - Sync projects across devices

## 🤝 Contributing
We welcome contributions from the community! Here's how you can help:

1. **Fork** the repository
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

Please ensure your code follows our coding standards and includes appropriate tests.

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋 Support & Feedback
Having issues or suggestions? We'd love to hear from you!

- 🐛 **Bug Reports**: [Create an issue](https://github.com/yourusername/mobile-ide/issues/new?assignees=&labels=bug&template=bug_report.md&title=)
- 💡 **Feature Requests**: [Submit a request](https://github.com/yourusername/mobile-ide/issues/new?assignees=&labels=enhancement&template=feature_request.md&title=)
- 💬 **General Discussion**: [Start a discussion](https://github.com/yourusername/mobile-ide/discussions)

## 🌟 Show Your Support
If you find this project useful, please consider:

- ⭐ **Starring** the repository
- 🐦 **Tweeting** about it
- 📝 **Writing** a blog post or tutorial
- 🎥 **Creating** a YouTube video review

---

<p align="center">
  <strong>Made with ❤️ for developers who code on the go!</strong>
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/placeholder-footer-icon.png" width="50" alt="Mobile IDE Logo"/>
</p>