package com.meta.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.room.Room
import com.meta.littlelemon.screens.Navigation
import com.meta.littlelemon.ui.theme.LittleLemonTheme
import com.meta.littlelemon.util.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }

    private val userRepository by lazy {
        val sharedPreferences = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        UserSharedPreferencesRepository(sharedPreferences)
    }

    private val httpClient by lazy {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
                json(contentType = ContentType("text", "plain"))
            }
        }
    }

    private val menuRepository by lazy {
        MenuRepositoryWithDatabaseAndNetworking(database.menuDao(), httpClient)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn = userRepository.loadUser()
        setContent {
            CompositionLocalProvider(
                LocalUserRepository provides userRepository,
                LocalMenuRepository provides menuRepository
            ) {
                LittleLemonTheme {
                    Navigation(isLoggedIn = isLoggedIn)
                }
            }
        }
    }
}