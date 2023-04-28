package com.meta.littlelemon.util

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

interface MenuRepository {
    fun filteredItems(searchPhrase: String, category: MenuItem.Category?): Flow<List<MenuItem>>
}

val LocalMenuRepository = compositionLocalOf<MenuRepository> { MenuRepositoryStub() }

private class MenuRepositoryStub: MenuRepository {
    override fun filteredItems(
        searchPhrase: String,
        category: MenuItem.Category?
    ): Flow<List<MenuItem>> = emptyFlow()
}

@OptIn(ExperimentalCoroutinesApi::class)
class MenuRepositoryWithDatabaseAndNetworking(
    private val menuDao: MenuDao,
    private val httpClient: HttpClient
): MenuRepository {
    private val allItemsLiveData = liveData {
        menuDao.getAllMenuItems()
            .mapLatest {
                println("fetch from db")
                it.ifEmpty {
                    val menu = httpClient.fetchMenu()
                    menuDao.save(menu)
                    menu
                }
            }
            .collect(::emit)
    }

    override fun filteredItems(
        searchPhrase: String,
        category: MenuItem.Category?
    ): Flow<List<MenuItem>> = allItemsLiveData
        .asFlow()
        .mapLatest { menu ->
            println("filteredItems.map")
            menu
                .filter {
                    it.title.contains(searchPhrase, ignoreCase = true) &&
                            if (category == null) true else it.category == category
                }
                .sortedBy { it.title }
        }
        .flowOn(Dispatchers.IO)
}

