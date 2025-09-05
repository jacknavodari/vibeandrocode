package com.example.ide.data.model

data class CodeFile(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val content: String,
    val extension: String,
    val language: String,
    val lastModified: Long = System.currentTimeMillis(),
    val isModified: Boolean = false
)

enum class FileExtension(val extension: String, val language: String, val mimeType: String) {
    HTML("html", "HTML", "text/html"),
    CSS("css", "CSS", "text/css"),
    JAVASCRIPT("js", "JavaScript", "text/javascript"),
    TYPESCRIPT("ts", "TypeScript", "text/typescript"),
    PYTHON("py", "Python", "text/x-python"),
    JAVA("java", "Java", "text/x-java-source"),
    KOTLIN("kt", "Kotlin", "text/x-kotlin"),
    CPP("cpp", "C++", "text/x-c++src"),
    C("c", "C", "text/x-csrc"),
    CSHARP("cs", "C#", "text/x-csharp"),
    PHP("php", "PHP", "text/x-php"),
    RUBY("rb", "Ruby", "text/x-ruby"),
    GO("go", "Go", "text/x-go"),
    RUST("rs", "Rust", "text/x-rustsrc"),
    SWIFT("swift", "Swift", "text/x-swift"),
    SQL("sql", "SQL", "text/x-sql"),
    JSON("json", "JSON", "application/json"),
    XML("xml", "XML", "text/xml"),
    YAML("yaml", "YAML", "text/x-yaml"),
    MARKDOWN("md", "Markdown", "text/x-markdown"),
    TXT("txt", "Plain Text", "text/plain");

    companion object {
        fun fromExtension(ext: String): FileExtension {
            return values().find { it.extension.equals(ext, ignoreCase = true) } ?: TXT
        }
    }
}

data class Project(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val files: MutableList<CodeFile> = mutableListOf(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis()
)