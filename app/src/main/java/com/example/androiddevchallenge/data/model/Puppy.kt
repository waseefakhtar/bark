package com.example.androiddevchallenge.data.model

data class Puppy(
    val id: Int,
    val title: String,
    val description: String,
    val puppyImageId: Int = 0
)