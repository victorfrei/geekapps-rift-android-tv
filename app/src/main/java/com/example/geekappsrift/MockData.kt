package com.example.geekappsrift

import androidx.annotation.DrawableRes

data class GameBundle(
    val name: String,
    val priceLabel: String,
    val originalPriceLabel: String? = null,
    val discountPercent: Int? = null,
    val proPriceLabel: String? = null,
    val includes: List<String>,
    val offerEndsLabel: String? = null
)

data class GameAddOn(
    val name: String,
    val priceLabel: String
)

data class Game(
    val id: String,
    val title: String,
    @DrawableRes val coverRes: Int,
    @DrawableRes val backgroundRes: Int,
    val trophiesCount: String = "2/10",
    val friendsPlayingCount: Int = 3,
    val hoursPlayed: Int = 0,
    val studio: String = "Studio",
    val releaseYear: String = "2024",
    val genre: String = "Ação",
    val ageRating: String = "16",
    val contentDescriptors: String = "Violência, Linguagem Imprópria",
    val priceLabel: String = "R$ 199,90",
    val originalPriceLabel: String? = null,
    val discountPercent: Int? = null,
    val proPriceLabel: String? = null,
    val offerEndsLabel: String? = null,
    val trialMinutes: Int = 60,
    val tags: List<String> = listOf("Multijogador Online", "Compatível com Cloud"),
    val description: String = "Explore um mundo aberto vibrante com liberdade total para " +
        "criar sua própria jornada. Enfrente desafios, personalize sua experiência e " +
        "jogue com amigos em partidas online a qualquer momento...",
    val bundles: List<GameBundle> = emptyList(),
    val addOns: List<GameAddOn> = emptyList()
)

private fun standardBundles(
    title: String,
    standard: String,
    standardOriginal: String,
    gold: String,
    goldOriginal: String,
    ultimate: String,
    ultimateOriginal: String
) = listOf(
    GameBundle(
        name = "$title Edição Padrão",
        priceLabel = standard,
        originalPriceLabel = standardOriginal,
        discountPercent = 30,
        proPriceLabel = null,
        includes = listOf(title),
        offerEndsLabel = "Oferta termina em 11/01, 12:00"
    ),
    GameBundle(
        name = "$title Gold Edition",
        priceLabel = gold,
        originalPriceLabel = goldOriginal,
        discountPercent = 30,
        includes = listOf(title, "Passe de Temporada", "Pacote de Skins", "E mais"),
        offerEndsLabel = "Oferta termina em 11/01, 12:00"
    ),
    GameBundle(
        name = "$title Ultimate Edition",
        priceLabel = ultimate,
        originalPriceLabel = ultimateOriginal,
        includes = listOf(title, "Passe de Temporada", "Conteúdo Exclusivo")
    )
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
        hoursPlayed = 62,
        studio = "Playground Games",
        releaseYear = "2021",
        genre = "Corrida",
        ageRating = "L",
        contentDescriptors = "Violência Leve",
        priceLabel = "R$ 174,90",
        originalPriceLabel = "R$ 249,90",
        discountPercent = 30,
        proPriceLabel = "R$ 99,90",
        offerEndsLabel = "Oferta termina em 11/01, 12:00",
        trialMinutes = 120,
        tags = listOf("Multijogador Online", "Mundo Aberto", "2-12 jogadores online"),
        description = "Dirija por paisagens deslumbrantes do México em um mundo aberto " +
            "sempre em evolução. Colecione centenas de carros, personalize cada detalhe " +
            "e dispute corridas com amigos em eventos sazonais que mudam toda semana...",
        bundles = standardBundles(
            title = "Forza Horizon 5",
            standard = "R$ 174,90",
            standardOriginal = "R$ 249,90",
            gold = "R$ 244,90",
            goldOriginal = "R$ 349,90",
            ultimate = "R$ 314,90",
            ultimateOriginal = "R$ 449,90"
        ),
        addOns = listOf(
            GameAddOn("Pacote de Carros Premium", "R$ 79,90"),
            GameAddOn("Passe de Expansão", "R$ 129,90"),
            GameAddOn("Pacote de Personalização", "R$ 39,90")
        )
    ),
    Game(
        id = "2",
        title = "Minecraft",
        coverRes = R.drawable.minecraft_cover,
        backgroundRes = R.drawable.minecraft_bg,
        trophiesCount = "8/10",
        friendsPlayingCount = 2,
        hoursPlayed = 145,
        studio = "Mojang Studios",
        releaseYear = "2011",
        genre = "Sandbox",
        ageRating = "L",
        contentDescriptors = "Violência Fantasiosa",
        priceLabel = "R$ 99,90",
        trialMinutes = 90
    ),
    Game(
        id = "3",
        title = "Halo Infinite",
        coverRes = R.drawable.halo_cover,
        backgroundRes = R.drawable.halo_bg,
        trophiesCount = "3/10",
        friendsPlayingCount = 5,
        hoursPlayed = 28,
        studio = "343 Industries",
        releaseYear = "2021",
        genre = "Tiro em Primeira Pessoa",
        ageRating = "16",
        contentDescriptors = "Violência, Linguagem Imprópria",
        priceLabel = "R$ 199,90",
        trialMinutes = 60
    ),
    Game(
        id = "4",
        title = "Party Animals",
        coverRes = R.drawable.party_animals_cover,
        backgroundRes = R.drawable.party_animals_bg,
        trophiesCount = "1/10",
        friendsPlayingCount = 3,
        hoursPlayed = 81,
        studio = "Recreate Games",
        releaseYear = "2023",
        genre = "Festa",
        ageRating = "L",
        contentDescriptors = "Violência Cômica",
        priceLabel = "R$ 89,90",
        trialMinutes = 60
    ),
    Game(
        id = "5",
        title = "Fortnite",
        coverRes = R.drawable.fortnite_cover,
        backgroundRes = R.drawable.fortnite_bg,
        trophiesCount = "7/10",
        friendsPlayingCount = 6,
        hoursPlayed = 103,
        studio = "Epic Games",
        releaseYear = "2017",
        genre = "Battle Royale",
        ageRating = "12",
        contentDescriptors = "Violência",
        priceLabel = "Grátis",
        trialMinutes = 0
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
