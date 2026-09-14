package com.example.geekappsrift

import androidx.annotation.DrawableRes

data class Game(
    val id: String,
    val title: String,
    @DrawableRes val coverRes: Int,
    @DrawableRes val backgroundRes: Int,
    val trophiesCount: String = "2/10",
    val friendsPlayingCount: Int = 3,
    val hoursPlayed: Int = 0
)

data class NewsItem(
    val id: String,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int
)

data class Friend(
    val id: String,
    val name: String,
    val statusText: String,
    val isOnline: Boolean,
    val avatarColor: Long,
    @DrawableRes val backgroundRes: Int
)

val mockGames = listOf(
    Game(
        id = "1",
        title = "Forza Horizon 5",
        coverRes = R.drawable.forza_cover,
        backgroundRes = R.drawable.forza_bg,
        trophiesCount = "5/10",
        friendsPlayingCount = 4,
        hoursPlayed = 62
    ),
    Game(
        id = "2",
        title = "Minecraft",
        coverRes = R.drawable.minecraft_cover,
        backgroundRes = R.drawable.minecraft_bg,
        trophiesCount = "8/10",
        friendsPlayingCount = 2,
        hoursPlayed = 145
    ),
    Game(
        id = "3",
        title = "Halo Infinite",
        coverRes = R.drawable.halo_cover,
        backgroundRes = R.drawable.halo_bg,
        trophiesCount = "3/10",
        friendsPlayingCount = 5,
        hoursPlayed = 28
    ),
    Game(
        id = "4",
        title = "Party Animals",
        coverRes = R.drawable.party_animals_cover,
        backgroundRes = R.drawable.party_animals_bg,
        trophiesCount = "1/10",
        friendsPlayingCount = 3,
        hoursPlayed = 81
    ),
    Game(
        id = "5",
        title = "Fortnite",
        coverRes = R.drawable.fortnite_cover,
        backgroundRes = R.drawable.fortnite_bg,
        trophiesCount = "7/10",
        friendsPlayingCount = 6,
        hoursPlayed = 103
    )
)

val mostPlayedGames = mockGames.sortedByDescending { it.hoursPlayed }

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

val mockFriends = listOf(
    Friend(
        id = "1",
        name = "Lucas Andrade",
        statusText = "Jogando Forza Horizon 5",
        isOnline = true,
        avatarColor = 0xFF4CAF50,
        backgroundRes = R.drawable.forza_bg
    ),
    Friend(
        id = "2",
        name = "Marina Souza",
        statusText = "Jogando Minecraft",
        isOnline = true,
        avatarColor = 0xFF2196F3,
        backgroundRes = R.drawable.minecraft_bg
    ),
    Friend(
        id = "3",
        name = "Pedro Lima",
        statusText = "Online",
        isOnline = true,
        avatarColor = 0xFFFF9800,
        backgroundRes = R.drawable.halo_bg
    ),
    Friend(
        id = "4",
        name = "Julia Costa",
        statusText = "Jogando Halo Infinite",
        isOnline = true,
        avatarColor = 0xFFE91E63,
        backgroundRes = R.drawable.halo_bg
    ),
    Friend(
        id = "5",
        name = "Rafael Dias",
        statusText = "Offline",
        isOnline = false,
        avatarColor = 0xFF9C27B0,
        backgroundRes = R.drawable.party_animals_bg
    )
)
