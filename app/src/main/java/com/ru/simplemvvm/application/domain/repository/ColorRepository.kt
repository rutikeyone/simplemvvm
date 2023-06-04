package com.ru.simplemvvm.application.domain.repository

import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.foundation.model.Repository
import kotlinx.coroutines.flow.Flow

interface ColorRepository : Repository {
    suspend fun getAvailableColors(): List<NamedColorModel>
    suspend fun getById(id: Long): NamedColorModel
    suspend fun getCurrentColor(): NamedColorModel
    fun setCurrentColor(color:NamedColorModel): Flow<Int>
    fun listenCurrentColor(): Flow<NamedColorModel>
}