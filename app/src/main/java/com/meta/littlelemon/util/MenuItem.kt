package com.meta.littlelemon.util

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class MenuItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    @TypeConverters(CategoryConverter::class) val category: Category?
) {
    enum class Category {
        Main,
        Dessert
    }

    companion object {
        val mock = MenuItem(
            id = -1,
            title = "Greek Salad",
            description = "The famous greek salad of crispy lettuce, peppers, olives and out Chicago",
            price = "12.99",
            image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
            category = null
        )
    }
}

private object CategoryConverter {
    @TypeConverter
    fun toMyEnum(value: String?): MenuItem.Category? =
        if (value != null) try {
            MenuItem.Category.valueOf(value)
        } catch(error: IllegalArgumentException) {
            null
        } else null

    @TypeConverter
    fun fromMyEnum(value: MenuItem.Category?): String? = value?.name
}