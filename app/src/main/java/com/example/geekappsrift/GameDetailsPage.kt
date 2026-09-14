package com.example.geekappsrift

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GameDetailsPage(
    game: Game,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            // Scroll the page explicitly on D-pad up/down. Relying on
            // focus-driven bring-into-view doesn't move this column at
            // all on a TV remote.
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown) return@onKeyEvent false
                if (event.nativeKeyEvent.repeatCount != 0) return@onKeyEvent true
                when (event.key) {
                    Key.DirectionDown -> {
                        coroutineScope.launch { scrollState.animateScrollBy(320f) }
                        true
                    }
                    Key.DirectionUp -> {
                        coroutineScope.launch { scrollState.animateScrollBy(-320f) }
                        true
                    }
                    else -> false
                }
            }
    ) {
        // Hero background
        AsyncImage(
            model = game.backgroundRes,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.75f),
                            Color.Black.copy(alpha = 0.55f),
                            Color.Black.copy(alpha = 0.97f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.85f), Color.Transparent),
                        endX = 1400f
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp)
        ) {
            // Left column stays put ("sticky") — it's simply outside the
            // scrollable container, so the purchase actions are always
            // reachable no matter how far the right side is scrolled.
            Column(
                modifier = Modifier
                    .width(320.dp)
                    .padding(top = 56.dp)
            ) {
                    Text(
                        text = "${game.studio} · ${game.releaseYear}",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = game.title,
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Jogo", color = Color.LightGray, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    if (game.trialMinutes > 0) {
                        DetailActionButton(
                            containerColor = Brush.horizontalGradient(
                                listOf(Color(0xFFFF6A00), Color(0xFFEE0979))
                            ),
                            requestInitialFocus = true,
                            priceContent = {
                                Text(
                                    text = "Jogar por ${game.trialMinutes} min",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ou compre",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    DetailActionButton(
                        priceContent = {
                            if (game.originalPriceLabel != null) {
                                Text(
                                    text = game.originalPriceLabel,
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(
                                text = "${game.priceLabel} Comprar",
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        containerColor = Brush.linearGradient(listOf(Color.White, Color.White)),
                        requestInitialFocus = game.trialMinutes == 0
                    )

                    if (game.proPriceLabel != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailActionButton(
                            priceContent = {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xFFEE0979), Color(0xFFFF6A00))
                                            )
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${game.proPriceLabel} Comprar",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            containerColor = Brush.linearGradient(
                                listOf(Color.White.copy(alpha = 0.1f), Color.White.copy(alpha = 0.1f))
                            )
                        )
                    }

                    if (game.offerEndsLabel != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = game.offerEndsLabel,
                            color = Color.Gray,
                            fontSize = 11.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ver todos os pacotes do jogo",
                        color = Color(0xFFFF8A50),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

            Spacer(modifier = Modifier.width(32.dp))

            // Right column scrolls: tags, rating, media, promo, bundles,
            // add-ons — the full page content.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(top = 56.dp, bottom = 48.dp)
            ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        game.tags.forEach { tag ->
                            TagChip(tag)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(2.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = game.ageRating,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Classificação ${game.ageRating}",
                                color = Color(0xFFFF8A50),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = game.contentDescriptors,
                                color = Color.LightGray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Large, roughly half-width previews — not small
                    // thumbnails — matching the reference's proportions.
                    Box {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MediaThumbnail(
                                imageRes = game.backgroundRes,
                                isVideo = true,
                                modifier = Modifier.weight(1f)
                            )
                            MediaThumbnail(
                                imageRes = game.coverRes,
                                isVideo = false,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.ChevronRight,
                                contentDescription = "Ver mais mídia",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = game.description,
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Mostrar mais",
                        color = Color(0xFFFF8A50),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    PromoBanner(game = game)

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Pacotes do jogo",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        game.bundles.forEach { bundle ->
                            BundleCard(
                                bundle = bundle,
                                imageRes = game.coverRes,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Complementos",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        game.addOns.forEach { addOn ->
                            AddOnCard(
                                addOn = addOn,
                                imageRes = game.backgroundRes,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
            }
        }

        // Back arrow
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 32.dp, top = 32.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun DetailActionButton(
    containerColor: Brush,
    requestInitialFocus: Boolean = false,
    priceContent: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (requestInitialFocus) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(containerColor)
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.White else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = priceContent
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun PromoBanner(game: Game, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.White else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .focusable(interactionSource = interactionSource),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(listOf(Color(0xFFEE0979), Color(0xFFFF6A00)))
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "PRO",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tenha este jogo com desconto na assinatura Pro",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Assine o Pro e tenha acesso imediato a jogos com até 20% de desconto.",
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1
            )
        }

        AsyncImage(
            model = game.coverRes,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(140.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun BundleCard(bundle: GameBundle, imageRes: Int, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.White else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .focusable(interactionSource = interactionSource)
    ) {
        Box {
            AsyncImage(
                model = imageRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
            if (bundle.proPriceLabel != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    ProBadge()
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = bundle.proPriceLabel,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = bundle.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (bundle.originalPriceLabel != null) {
                    Text(
                        text = bundle.originalPriceLabel,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = bundle.priceLabel,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                if (bundle.discountPercent != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF1E8E5A))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "-${bundle.discountPercent}%",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            bundle.includes.forEach { item ->
                Text(
                    text = "• $item",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
            if (bundle.offerEndsLabel != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = bundle.offerEndsLabel,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun AddOnCard(addOn: GameAddOn, imageRes: Int, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.White else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .focusable(interactionSource = interactionSource)
    ) {
        AsyncImage(
            model = imageRes,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = addOn.name,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = addOn.priceLabel, color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ProBadge() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFEE0979), Color(0xFFFF6A00))))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = "PRO", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TagChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 11.sp)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun MediaThumbnail(imageRes: Int, isVideo: Boolean, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = if (isFocused) 3.dp else 0.dp,
                color = if (isFocused) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(6.dp)
            )
            .focusable(interactionSource = interactionSource)
    ) {
        AsyncImage(
            model = imageRes,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (isVideo) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = "Reproduzir vídeo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
