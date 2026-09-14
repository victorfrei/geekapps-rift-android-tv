package com.example.geekappsrift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.geekappsrift.ui.theme.GeekappsRiftTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeekappsRiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedGame by remember { mutableStateOf(mockGames.first()) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image with crossfade
        Crossfade(
            targetState = selectedGame.backgroundRes,
            animationSpec = tween(durationMillis = 500),
            label = "background_fade"
        ) { bgRes ->
            AsyncImage(
                model = bgRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.95f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Main Vertical Scroll Container
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            // Header is top item
            item {
                TopNavigationBar()
                Spacer(modifier = Modifier.height(48.dp))
            }

            // Game Details Meta Info (Title, Trophies, Friends)
            item {
                GameMetaSection(selectedGame)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Game Carousel
            item {
                GameCarousel(
                    games = mockGames,
                    onGameSelected = { selectedGame = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Novidades Teaser / Scroll Hint
            item {
                NovidadesTeaser()
                Spacer(modifier = Modifier.height(64.dp))
            }

            // Novidades Section (Appears on Scroll Down)
            item {
                NovidadesSection()
            }
        }

        // Fixed Bottom Hints on overlay
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 48.dp)
        ) {
            BottomHints()
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TopNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 48.dp, end = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: time & icons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("20:32", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Rounded.Search, contentDescription = "Search", tint = Color.White, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(12.dp))
        }

        // Center: Tabs
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("PLAY", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            Text("COMMUNITY", color = Color.LightGray, fontSize = 10.sp)
            Text("ENTERTAINMENT", color = Color.LightGray, fontSize = 10.sp)
            Text("STORE", color = Color.LightGray, fontSize = 10.sp)
        }

        // Right side: Profile
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Victor Freire ", color = Color.LightGray, fontSize = 9.sp)
            Text("victorfrei", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Rounded.AccountCircle, contentDescription = "Profile", tint = Color.Green, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun GameMetaSection(game: Game) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)
    ) {
        // Game Title
        Text(
            text = game.title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Trophy and Friends Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Trophies
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🏆 ", fontSize = 14.sp)
                Text(
                    text = game.trophiesCount,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Friends Playing
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${game.friendsPlayingCount} amigos jogando ",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-6).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(game.friendsPlayingCount.coerceAtMost(3)) { index ->
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(
                                    when (index) {
                                        0 -> Color(0xFF4CAF50)
                                        1 -> Color(0xFF2196F3)
                                        else -> Color(0xFFFF9800)
                                    }
                                )
                                .border(1.dp, Color.Black, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GameCarousel(
    games: List<Game>,
    onGameSelected: (Game) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 48.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        // Small icon button at far left as seen in wireframe [x]
        item {
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsFocusedAsState()

            Box(
                modifier = Modifier
                    .size(48.dp, 180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .border(
                        width = if (isFocused) 2.dp else 0.dp,
                        color = if (isFocused) Color.Green else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .focusable(interactionSource = interactionSource),
                contentAlignment = Alignment.Center
            ) {
                @OptIn(ExperimentalTvMaterial3Api::class)
                Icon(
                    Icons.Rounded.Apps,
                    contentDescription = "Library",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Game cards
        items(games) { game ->
            GameCard(game = game, onFocused = { onGameSelected(game) })
        }
    }
}

@Composable
fun GameCard(
    game: Game,
    onFocused: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        if (isFocused) {
            onFocused()
        }
    }

    val width by animateDpAsState(if (isFocused) 150.dp else 120.dp, label = "width_anim")
    val height by animateDpAsState(if (isFocused) 225.dp else 180.dp, label = "height_anim")

    Box(
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .focusable(interactionSource = interactionSource)
    ) {
        AsyncImage(
            model = game.coverRes,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun NovidadesTeaser() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(2.dp)
                    .background(Color.Green)
            )
            Text(
                text = "NOVIDADES",
                color = Color.Green,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                Icons.Rounded.KeyboardArrowDown,
                contentDescription = "Scroll down",
                tint = Color.Green,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun NovidadesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)
    ) {
        Text(
            text = "NOVIDADES E DESTAQUES",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 48.dp)
        ) {
            items(mockNews) { news ->
                NewsCard(news)
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsItem) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .size(240.dp, 135.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .focusable(interactionSource = interactionSource)
    ) {
        AsyncImage(
            model = news.imageRes,
            contentDescription = news.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                        startY = 40f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = news.title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = news.subtitle,
                color = Color.LightGray,
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun BottomHints() {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HintIcon("A", "Select")
        Spacer(modifier = Modifier.width(16.dp))
        HintIcon("Y", "Search")
    }
}

@Composable
fun HintIcon(button: String, action: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(Color.DarkGray.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = button, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = action, color = Color.LightGray, fontSize = 12.sp)
    }
}
