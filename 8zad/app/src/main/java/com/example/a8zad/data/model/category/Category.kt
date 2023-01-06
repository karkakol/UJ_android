package com.example.a8zad.data.model.category

data class Category (
    val id: Int,
    val name: String,
   val desc: String,
    val color: String){

    override fun toString(): String {
        return name
    }
}

