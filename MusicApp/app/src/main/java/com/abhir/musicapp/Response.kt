package com.abhir.musicapp

data class Response(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)