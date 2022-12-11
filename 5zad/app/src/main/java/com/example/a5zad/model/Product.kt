package com.example.a5zad.model

data class Product(val name: String,val desc: String){

    companion object{
       fun getProducts(): List<Product>{
           return listOf(
               Product("Kamień", "Kamień jest twardy"),
               Product("Paper", "Papier jest cienki"),
               Product("Nożyce", "Nożyce są ostre"),
               Product("Koszulka", "Koszulka jest wełniania"),
               Product("Dziura", "Dziura jest czarna"),
           )
       }
    }
}