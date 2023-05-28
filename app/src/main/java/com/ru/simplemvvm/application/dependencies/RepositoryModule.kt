package com.ru.simplemvvm.application.dependencies

import com.ru.simplemvvm.application.data.FirstRepository
import com.ru.simplemvvm.application.data.FirstRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFirstRepository(firstRepository: FirstRepositoryImpl): FirstRepository
}