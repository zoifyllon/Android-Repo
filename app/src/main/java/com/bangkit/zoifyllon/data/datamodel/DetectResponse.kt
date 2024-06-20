package com.bangkit.zoifyllon.data.datamodel

data class Disease(
    val disease_id: Int,
    val history_id: Int,
    val disease: String,
    val percentage: Float,
    val symptoms: List<String>,
    val prevents: List<String>,
)

data class Data(
    val id: Int,
    val image_url: String,
    val user_id: Int,
    val diseases: List<Disease>
)

data class DetectResponse(
    val message: String,
    val data: Data
)
