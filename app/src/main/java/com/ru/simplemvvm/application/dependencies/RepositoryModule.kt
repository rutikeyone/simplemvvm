package com.ru.simplemvvm.application.dependencies

import com.ru.simplemvvm.application.data.repository.InMemoryColorsRepository
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindColorRepository(colorRepository: InMemoryColorsRepository): ColorRepository
}