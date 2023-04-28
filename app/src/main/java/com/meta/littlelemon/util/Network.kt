package com.meta.littlelemon.util

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

suspend fun HttpClient.fetchMenu(url: String = defaultMenuURL) =
    get(url).body<MenuNetwork>().toMenu()

@Serializable
private data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
) {
    fun toMenuItem() = MenuItem(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = when(category) {
            "mains" -> MenuItem.Category.Main
            "desserts" -> MenuItem.Category.Dessert
            else -> null
        }
    )
}

@Serializable
private data class MenuNetwork(
    val menu: List<MenuItemNetwork>
) {
    fun toMenu() = menu.map(MenuItemNetwork::toMenuItem)
}

private val defaultMenuURL
    = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
