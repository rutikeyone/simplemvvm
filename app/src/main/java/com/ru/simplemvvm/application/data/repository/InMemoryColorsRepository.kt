package com.ru.simplemvvm.application.data.repository

import android.graphics.Color
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import com.ru.simplemvvm.foundation.model.coroutines.IoDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InMemoryColorsRepository @Inject constructor(
    private val ioDispatchers: IoDispatcher,
): ColorRepository {
    private var currentColor: NamedColorModel = AVAILABLE_COLORS[0]

    private val currentColorFlow = MutableSharedFlow<NamedColorModel>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    override suspend fun getAvailableColors(): List<NamedColorModel> = withContext(ioDispatchers.value) {
        delay(1000)
        return@withContext AVAILABLE_COLORS
    }

    override suspend fun getById(id: Long): NamedColorModel = withContext(ioDispatchers.value) {
        delay(1000)
        return@withContext AVAILABLE_COLORS.first { it.id == id }
    }

    override suspend fun getCurrentColor(): NamedColorModel = withContext(ioDispatchers.value) {
        delay(1000)
        return@withContext currentColor
    }

    override fun setCurrentColor(color: NamedColorModel): Flow<Int> = flow {
        if(currentColor != color) {
            var progress = 0;
            while (progress < 100) {
                progress += 2
                delay(30)
                emit(progress)
            }
            currentColor = color
            currentColorFlow.emit(currentColor)
        } else {
            emit(100);
        }
    }.flowOn(ioDispatchers.value)

    override fun listenCurrentColor(): Flow<NamedColorModel> = currentColorFlow

    companion object {
        private val AVAILABLE_COLORS = listOf(
            NamedColorModel(1, "Red", Color.RED),
            NamedColorModel(2, "Green", Color.GREEN),
            NamedColorModel(3, "Blue", Color.BLUE),
            NamedColorModel(4, "Yellow", Color.YELLOW),
            NamedColorModel(5, "Magenta", Color.MAGENTA),
            NamedColorModel(6, "Cyan", Color.CYAN),
            NamedColorModel(7, "Gray", Color.GRAY),
            NamedColorModel(8, "Navy", Color.rgb(0, 0, 128)),
            NamedColorModel(9, "Pink", Color.rgb(255, 20, 147)),
            NamedColorModel(10, "Sienna", Color.rgb(160, 82, 45)),
            NamedColorModel(11, "Khaki", Color.rgb(240, 230, 140)),
            NamedColorModel(12, "Forest Green", Color.rgb(34, 139, 34)),
            NamedColorModel(13, "Sky", Color.rgb(135, 206, 250)),
            NamedColorModel(14, "Olive", Color.rgb(107, 142, 35)),
            NamedColorModel(15, "Violet", Color.rgb(148, 0, 211)),
        )
    }
}