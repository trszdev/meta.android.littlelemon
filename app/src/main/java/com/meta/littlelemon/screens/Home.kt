package com.meta.littlelemon.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.meta.littlelemon.R
import com.meta.littlelemon.components.Header
import com.meta.littlelemon.ui.theme.LittleLemonTheme
import com.meta.littlelemon.util.LocalMenuRepository
import com.meta.littlelemon.util.MenuItem
import com.meta.littlelemon.util.navigate
import com.valentinilk.shimmer.shimmer

@Composable
fun Home(navController: NavController) {
    var searchPhrase by rememberSaveable { mutableStateOf("") }
    var category: MenuItem.Category? by rememberSaveable { mutableStateOf(null) }
    val menuRepository = LocalMenuRepository.current
    val menuItems by
        remember(searchPhrase, category) {
            menuRepository.filteredItems(searchPhrase, category)
        }
        .collectAsState(initial = null)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        HeaderWithProfile(onProfileClick = {
            navController.navigate(Destination.Profile)
        })

        LazyColumn {
            item {
                SearchArea(value = searchPhrase, onValueChange = { searchPhrase = it })
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "ORDER FOR DELIVERY!",
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(top = 40.dp, bottom = 10.dp)
                    )

                    Categories(category = category, onValueChange = { category = it })

                    Divider(
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }

            items(menuItems)
        }
    }
}


private fun LazyListScope.items(menuItems: List<MenuItem>?) {
    if (menuItems != null) {
        items(menuItems, key = { it.id }) {
            MenuItemRow(it)
        }
    } else {
        items(10) {
            Box {
                SkeletonItemRow()
            }
        }
    }
}

@Composable
private fun SkeletonItemRow() {
    val shape = MaterialTheme.shapes.large
    val density = LocalDensity.current
    val titleSize = with(density) { MaterialTheme.typography.h6.fontSize.toDp() }
    val descriptionPriceSize = 4.dp + with(density) {
        MaterialTheme.typography.body1.fontSize.times(2).toDp() +
                14.sp.toDp() +
                MaterialTheme.typography.subtitle1.fontSize.toDp()
    }

    Column(modifier = Modifier.padding(
        top = 20.dp,
        start = 20.dp,
        end = 20.dp
    )) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .size(width = 130.dp, height = titleSize)
                        .shimmer()
                        .background(
                            MaterialTheme.colors.onSurface,
                            shape = shape
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(descriptionPriceSize)
                        .shimmer()
                        .background(
                            MaterialTheme.colors.onSurface.copy(alpha = .7f),
                            shape = shape
                        )
                )
            }

            Box(modifier = Modifier.padding(start = 10.dp)) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .shimmer()
                        .background(
                            MaterialTheme.colors.onSurface,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }

        }

        Divider(color = MaterialTheme.colors.onSurface)
    }
}

@Composable
private fun MenuItemRow(menuItem: MenuItem) {
    Column(modifier = Modifier
        .clickable {}
        .padding(
            top = 20.dp,
            start = 20.dp,
            end = 20.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    menuItem.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1
                )

                Text(
                    menuItem.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Text(
                    "$${menuItem.price}",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary,
                    maxLines = 1
                )
            }

            Box(modifier = Modifier.padding(start = 10.dp)) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .shimmer()
                        .background(
                            MaterialTheme.colors.onSurface,
                            shape = MaterialTheme.shapes.small
                        )
                )

                Image(
                    painter = rememberImagePainter(menuItem.image),
                    contentDescription = "${menuItem.title} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(75.dp)
                )
            }

        }

        Divider(color = MaterialTheme.colors.onSurface)
    }
}

@Composable
private fun Categories(category: MenuItem.Category?, onValueChange: (MenuItem.Category?) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
    ) {
        Category(
            "Mains",
            selected = category == MenuItem.Category.Main,
            onClick = {
                when (category) {
                    MenuItem.Category.Main -> onValueChange(null)
                    else -> onValueChange(MenuItem.Category.Main)
                }
            }
        )

        Category(
            "Desserts",
            selected = category == MenuItem.Category.Dessert,
            onClick = {
                when (category) {
                    MenuItem.Category.Dessert -> onValueChange(null)
                    else -> onValueChange(MenuItem.Category.Dessert)
                }
            }
        )
    }
}

@Composable
private fun Category(text: String, selected: Boolean = false, onClick: () -> Unit = {}) {
    val primary = MaterialTheme.colors.primary
    val secondary = MaterialTheme.colors.onSurface
    val onPrimary = MaterialTheme.colors.onPrimary

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) primary else secondary,
            contentColor = if (selected) onPrimary else primary
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
        modifier = Modifier.padding(end = 30.dp)
    ) {
        Text(text, style = MaterialTheme.typography.h5)
    }
}

@Composable
private fun SearchArea(value: String, onValueChange: (String) -> Unit = {}) {
    Surface(color = MaterialTheme.colors.primary) {
        Column(
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 20.dp,
                start = 20.dp,
                end = 20.dp,
            )
        ) {
            val density = LocalDensity.current
            val littleLemonHeight = with(density) { 54.sp.toDp() }

            Text(
                "Little Lemon",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primaryVariant,
                maxLines = 1,
                modifier = Modifier.height(littleLemonHeight),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Chicago",
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onPrimary,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Image(
                    painter = painterResource(R.drawable.hero),
                    contentDescription = "Little Lemon waiter",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(width = 145.dp, height = 155.dp)
                        .clip(RoundedCornerShape(percent = 15))
                )
            }

            TextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.subtitle1,
                placeholder = {
                    Text(
                        "Enter search phrase",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(.9f)
                    )
                },
                leadingIcon = { Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                ) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.onSurface,
                    placeholderColor = MaterialTheme.colors.onSecondary,
                    leadingIconColor = MaterialTheme.colors.onBackground
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HeaderWithProfile(onProfileClick: () -> Unit) {
    Box {
        Header()

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(88.dp)
                .padding(20.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .clickable(onClick = onProfileClick)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LittleLemonTheme {
        Home(rememberNavController())
    }
}