package com.itdev.aplikasicrud

data class DataWarga(
    val id : String,
    val nama: String,
    val umur : Int

){
    constructor(): this("", "", 0){

    }
}