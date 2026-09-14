package com.example.geekappsrift

import androidx.annotation.DrawableRes

data class Game(
    val id: String,
    val title: String,
    @DrawableRes val coverRes: Int,
    @DrawableRes val backgroundRes: Int,
    val trophiesCount: String = "2/10",
    val friendsPlayingCount: Int = 3
)

data class NewsItem(
    val id: String,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int
)

val mockGames = listOf(
    Game(
        id = "1",
        title = "Forza Horizon 5",
        coverRes = R.drawable.forza_cover,
        backgroundRes = R.drawable.forza_bg,
        trophiesCount = "5/10",
        friendsPlayingCount = 4
    ),
    Game(
        id = "2",
        title = "Minecraft",
        coverRes = R.drawable.minecraft_cover,
        backgroundRes = R.drawable.minecraft_bg,
        trophiesCount = "8/10",
        friendsPlayingCount = 2
    ),
    Game(
        id = "3",
        title = "Halo Infinite",
        coverRes = R.drawable.halo_cover,
        backgroundRes = R.drawable.halo_bg,
        trophiesCount = "3/10",
        friendsPlayingCount = 5
    ),
    Game(
        id = "4",
        title = "Party Animals",
        coverRes = R.drawable.party_animals_cover,
        backgroundRes = R.drawable.party_animals_bg,
        trophiesCount = "1/10",
        friendsPlayingCount = 3
    ),
    Game(
        id = "5",
        title = "Fortnite",
        coverRes = R.drawable.fortnite_cover,
        backgroundRes = R.drawable.fortnite_bg,
        trophiesCount = "7/10",
        friendsPlayingCount = 6
    )
)

val mockNews = listOf(
    NewsItem(
        id = "1",
        title = "Nova Temporada Lançada!",
        subtitle = "Confira todas as novidades e itens exclusivos.",
        imageRes = R.drawable.fortnite_bg
    ),
    NewsItem(
        id = "2",
        title = "Evento de Fim de Semana",
        subtitle = "Ganhe XP em dobro em todas as partidas.",
        imageRes = R.drawable.halo_bg
    ),
    NewsItem(
        id = "3",
        title = "Atualização de Conteúdo",
        subtitle = "Novos carros e pistas adicionados hoje.",
        imageRes = R.drawable.forza_bg
    ),
    NewsItem(
        id = "4",
        title = "Novos Mapas Disponíveis",
        subtitle = "Jogue agora nos cenários inéditos.",
        imageRes = R.drawable.party_animals_bg
    )
)
