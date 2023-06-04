package com.ru.simplemvvm.application.dependencies

import com.ru.simplemvvm.foundation.model.coroutines.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesModule {
    @Provides
    @Singleton
    fun provideIoDispatchers(): IoDispatcher {
        return IoDispatcher();
    }
}