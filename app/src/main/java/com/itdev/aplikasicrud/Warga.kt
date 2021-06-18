package com.itdev.aplikasicrud

data class Warga(
    val id : String,
    val nama: String,
    val asal : String

){
    constructor(): this("", "", ""){

    }
}