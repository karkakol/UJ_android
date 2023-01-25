package com.example.a8zad.utils

class EmailValidator {
    companion object{
        fun validate(email: String): Boolean{
            return Regex("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$").matches(email)
        }
    }
}