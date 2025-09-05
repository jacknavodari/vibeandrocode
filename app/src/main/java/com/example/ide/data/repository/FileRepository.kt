package com.example.ide.data.repository

import android.content.Context
import android.os.Environment
import com.example.ide.data.model.CodeFile
import com.example.ide.data.model.FileExtension
import com.example.ide.data.model.Project
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter

class FileRepository(
    private val context: Context
) {
    private val gson = Gson()
    private val projectsFile = File(context.filesDir, "projects.json")
    
    // Create a projects directory in Downloads
    private val projectsDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "IDEProjects")
    
    init {
        // Ensure the projects directory exists
        if (!projectsDir.exists()) {
            projectsDir.mkdirs()
        }
    }
    
    fun saveProject(project: Project) {
        val projects = getAllProjects().toMutableList()
        val existingIndex = projects.indexOfFirst { it.id == project.id }
        
        if (existingIndex >= 0) {
            projects[existingIndex] = project.copy(lastModified = System.currentTimeMillis())
        } else {
            projects.add(project)
        }
        
        // Keep only the most recent project (limit to 1)
        if (projects.size > 1) {
            // Sort by last modified date and keep only the most recent
            projects.sortByDescending { it.lastModified }
            // Remove all but the first (most recent) project
            while (projects.size > 1) {
                val projectToRemove = projects.removeAt(1)
                // Delete the project directory from file system
                val projectDir = File(projectsDir, projectToRemove.name)
                if (projectDir.exists()) {
                    deleteRecursively(projectDir)
                }
            }
        }
        
        saveProjects(projects)
        
        // Save the project files to the project directory
        saveProjectFiles(project)
    }
    
    fun getAllProjects(): List<Project> {
        return if (projectsFile.exists()) {
            try {
                val json = projectsFile.readText()
                val type = object : TypeToken<List<Project>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }
    
    fun deleteProject(projectId: String) {
        val projects = getAllProjects().toMutableList()
        val projectToDelete = projects.find { it.id == projectId }
        projects.removeAll { it.id == projectId }
        saveProjects(projects)
        
        // Delete project directory from file system
        projectToDelete?.let {
            val projectDir = File(projectsDir, it.name)
            if (projectDir.exists()) {
                deleteRecursively(projectDir)
            }
        }
    }
    
    private fun saveProjects(projects: List<Project>) {
        try {
            val json = gson.toJson(projects)
            projectsFile.writeText(json)
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    /**
     * Save project files to the project directory in Downloads
     */
    private fun saveProjectFiles(project: Project) {
        try {
            // Create project directory
            val projectDir = File(projectsDir, project.name)
            if (!projectDir.exists()) {
                projectDir.mkdirs()
            }
            
            // Save each file in the project
            for (file in project.files) {
                val fullFileName = if (file.name.endsWith(".${file.extension}")) {
                    file.name
                } else {
                    "${file.name}.${file.extension}"
                }
                
                val projectFile = File(projectDir, fullFileName)
                FileWriter(projectFile).use { writer ->
                    writer.write(file.content)
                }
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    /**
     * Save a code file directly to a project folder
     */
    fun saveFileToProject(projectName: String, fileName: String, content: String, extension: String): Result<String> {
        return try {
            // Create project directory if it doesn't exist
            val projectDir = File(projectsDir, projectName)
            if (!projectDir.exists()) {
                projectDir.mkdirs()
            }
            
            // Create the file with proper extension
            val fullFileName = if (fileName.endsWith(".$extension")) fileName else "$fileName.$extension"
            val file = File(projectDir, fullFileName)
            
            // Handle file name conflicts
            var counter = 1
            var finalFile = file
            while (finalFile.exists()) {
                val nameWithoutExt = fileName.substringBeforeLast(".")
                val finalFileName = "${nameWithoutExt}_$counter.$extension"
                finalFile = File(projectDir, finalFileName)
                counter++
            }
            
            FileWriter(finalFile).use { writer ->
                writer.write(content)
            }
            
            Result.success(finalFile.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun saveFileToDownloads(fileName: String, content: String, extension: String): Result<String> {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val fullFileName = if (fileName.endsWith(".$extension")) fileName else "$fileName.$extension"
            val file = File(downloadsDir, fullFileName)
            
            // Handle file name conflicts
            var counter = 1
            var finalFile = file
            while (finalFile.exists()) {
                val nameWithoutExt = fileName.substringBeforeLast(".")
                val finalFileName = "${nameWithoutExt}_$counter.$extension"
                finalFile = File(downloadsDir, finalFileName)
                counter++
            }
            
            FileWriter(finalFile).use { writer ->
                writer.write(content)
            }
            
            Result.success(finalFile.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun createNewFile(name: String, extension: String): CodeFile {
        val fileExtension = FileExtension.fromExtension(extension)
        return CodeFile(
            name = name,
            content = getTemplateContent(fileExtension),
            extension = extension,
            language = fileExtension.language
        )
    }
    
    private fun getTemplateContent(extension: FileExtension): String {
        return when (extension) {
            FileExtension.HTML -> """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
</body>
</html>"""
            
            FileExtension.PYTHON -> """#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def main():
    print("Hello, World!")

if __name__ == "__main__":
    main()"""
    
            FileExtension.JAVASCRIPT -> """// JavaScript file
console.log("Hello, World!");"""
    
            FileExtension.JAVA -> """public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}"""
    
            FileExtension.KOTLIN -> """fun main() {
    println("Hello, World!")
}"""
    
            FileExtension.CPP -> """#include <iostream>

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}"""
    
            FileExtension.CSS -> """/* CSS Styles */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 20px;
}"""
    
            else -> ""
        }
    }
    
    /**
     * Delete a file from a project both in memory and on disk
     */
    fun deleteFileFromProject(projectName: String, fileName: String, extension: String) {
        try {
            // Delete file from project directory
            val projectDir = File(projectsDir, projectName)
            if (projectDir.exists()) {
                val fullFileName = if (fileName.endsWith(".$extension")) fileName else "$fileName.$extension"
                val file = File(projectDir, fullFileName)
                if (file.exists()) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            // Handle error silently
        }
    }
    
    /**
     * Recursively delete a directory and all its contents
     */
    private fun deleteRecursively(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { child ->
                deleteRecursively(child)
            }
        }
        file.delete()
    }

}