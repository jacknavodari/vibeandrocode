package com.example.ide.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ide.data.repository.AIRepository
import com.example.ide.data.repository.FileRepository
import com.example.ide.ui.viewmodel.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> {
                MainViewModel(
                    aiRepository = AIRepository(),
                    fileRepository = FileRepository(context)
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}