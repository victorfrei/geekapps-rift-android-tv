package com.example.geekappsrift

data class Game(
    val id: String,
    val title: String,
    val coverUrl: String,
    val backgroundUrl: String
)

val mockGames = listOf(
    Game(
        id = "1",
        title = "Forza Horizon 5",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/8/86/Forza_Horizon_5_cover_art.jpg",
        backgroundUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?q=80&w=1920"
    ),
    Game(
        id = "2",
        title = "Minecraft",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png",
        backgroundUrl = "https://images.unsplash.com/photo-1606144042858-a5ea658dfa32?q=80&w=1920"
    ),
    Game(
        id = "3",
        title = "Diablo IV",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/6/68/Diablo_IV_cover_art.png",
        backgroundUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=1920"
    ),
    Game(
        id = "4",
        title = "Fortnite",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/a/ae/Fortnite_Save_The_World.jpg",
        backgroundUrl = "https://images.unsplash.com/photo-1589241062272-c0a1f43699ce?q=80&w=1920"
    ),
    Game(
        id = "5",
        title = "Party Animals",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/8/84/Party_Animals_cover_art.jpg",
        backgroundUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?q=80&w=1920"
    ),
    Game(
        id = "6",
        title = "Halo Infinite",
        coverUrl = "https://upload.wikimedia.org/wikipedia/en/1/14/Halo_Infinite.png",
        backgroundUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=1920"
    )
)
