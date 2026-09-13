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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var selectedGame by remember { mutableStateOf(mockGames.first()) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image with crossfade
        Crossfade(
            targetState = selectedGame.backgroundUrl,
            animationSpec = tween(durationMillis = 500),
            label = "background_fade"
        ) { bgUrl ->
            AsyncImage(
                model = bgUrl,
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
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            TopNavigationBar()
            Spacer(modifier = Modifier.weight(1f))
            GameCarousel(
                games = mockGames,
                onGameSelected = { selectedGame = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            BottomRow()
            Spacer(modifier = Modifier.height(16.dp))
            BottomHints()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BottomHints() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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
                .size(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = button, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = action, color = Color.LightGray, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TopNavigationBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: time & icons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("20:32", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Rounded.Search, contentDescription = "Search", tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(16.dp))
        }

        // Center: Tabs
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("PLAY", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("COMMUNITY", color = Color.LightGray, fontSize = 14.sp)
            Text("ENTERTAINMENT", color = Color.LightGray, fontSize = 14.sp)
            Text("STORE", color = Color.LightGray, fontSize = 14.sp)
        }

        // Right side: Profile
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Victor Freire ", color = Color.LightGray, fontSize = 12.sp)
            Text("victorfrei", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Rounded.AccountCircle, contentDescription = "Profile", tint = Color.Green, modifier = Modifier.size(32.dp))
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
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
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

    val width by animateDpAsState(if (isFocused) 320.dp else 160.dp, label = "width_anim")
    val height by animateDpAsState(if (isFocused) 320.dp else 160.dp, label = "height_anim")

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
            model = game.coverUrl,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        if (isFocused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 100f
                        )
                    )
            )
            Text(
                text = game.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun BottomRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .size(160.dp, 80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray.copy(alpha = 0.5f))
                    .focusable()
            )
        }
    }
}
