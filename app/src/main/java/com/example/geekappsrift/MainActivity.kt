package com.example.geekappsrift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedGame = mockGames[selectedIndex]

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

        Column(modifier = Modifier.fillMaxSize()) {
            // Header, game title/meta and the game row are all fixed in
            // place — none of this scrolls or moves due to focus changes.
            TopNavigationBar()
            Spacer(modifier = Modifier.height(200.dp))

            GameMetaSection(selectedGame)
            Spacer(modifier = Modifier.height(16.dp))

            GameCarousel(
                games = mockGames,
                selectedIndex = selectedIndex,
                onIndexSelected = { selectedIndex = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Only the Novidades area scrolls into view.
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 48.dp)
            ) {
                item {
                    NovidadesTeaser()
                    Spacer(modifier = Modifier.height(64.dp))
                }

                item {
                    NovidadesSection()
                }
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

private val CardSize = 136.dp
private val InactiveCardSize = CardSize * 0.7f // 30% smaller than the selected card
private val CarouselStartPadding = 48.dp

private val CardSpacing = 16.dp

@Composable
fun GameCarousel(
    games: List<Game>,
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    // The cursor never moves: it's a fixed overlay drawn at the first slot.
    // We fully own the row's horizontal offset (no LazyRow scroll state
    // involved) so nothing else can fight our animation or leave it stuck
    // mid-way. Every slot has a FIXED layout footprint (InactiveCardSize) so
    // the row never reflows — the selected card visually grows past its
    // slot via a child-size animation, it never changes the slot's own
    // layout size, which is what caused the jumpy motion before.
    val targetOffset = -(InactiveCardSize + CardSpacing) * selectedIndex
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = CarouselAnimationSpec,
        label = "carousel_offset"
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(CardSize)
            .clipToBounds()
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) return@onKeyEvent false
                // Ignore OS-level key-repeat dispatches (e.g. a slightly
                // delayed key-up over a network-connected remote/adb), so a
                // single press never advances more than one slot.
                if (event.nativeKeyEvent.repeatCount != 0) return@onKeyEvent true
                when (event.key) {
                    Key.DirectionRight -> {
                        if (selectedIndex < games.lastIndex) {
                            onIndexSelected(selectedIndex + 1)
                        }
                        true
                    }
                    Key.DirectionLeft -> {
                        if (selectedIndex > 0) {
                            onIndexSelected(selectedIndex - 1)
                        }
                        true
                    }
                    else -> false
                }
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(CardSpacing),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(start = CarouselStartPadding)
                .offset(x = animatedOffset)
        ) {
            games.forEachIndexed { index, game ->
                // Real reflow: the selected card's actual (bigger) width is
                // what the Row measures, so every card after it slides over
                // to make room instead of being covered by the overflow.
                GameCard(
                    game = game,
                    isSelected = index == selectedIndex,
                    modifier = Modifier.zIndex(if (index == selectedIndex) 1f else 0f)
                )
            }
        }

        // Fixed cursor: always drawn in the same place, on top of whichever
        // cover the row has slid into the first slot.
        Box(
            modifier = Modifier
                .padding(start = CarouselStartPadding)
                .size(CardSize)
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, Color.Green, RoundedCornerShape(8.dp))
        )
    }
}

// Spring-like motion with a slight overshoot before settling — "resistência
// considerável" means fairly damped, not a loose bouncy spring.
private val CarouselAnimationSpec = spring<Dp>(
    dampingRatio = 0.7f,
    stiffness = Spring.StiffnessMediumLow
)
private val CarouselAlphaAnimationSpec = spring<Float>(
    dampingRatio = 0.7f,
    stiffness = Spring.StiffnessMediumLow
)

@Composable
fun GameCard(game: Game, isSelected: Boolean, modifier: Modifier = Modifier) {
    // Both cards are always square — only the side length changes.
    val size by animateDpAsState(
        targetValue = if (isSelected) CardSize else InactiveCardSize,
        animationSpec = CarouselAnimationSpec,
        label = "card_size"
    )
    // Inactive cards sit under a 35% black overlay; the selected one clears
    // up to fully transparent.
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0f else 0.35f,
        animationSpec = CarouselAlphaAnimationSpec,
        label = "card_overlay"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = game.coverRes,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = overlayAlpha))
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
