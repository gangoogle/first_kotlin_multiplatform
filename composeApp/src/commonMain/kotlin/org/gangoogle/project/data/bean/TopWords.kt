package org.gangoogle.project.data.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopWords(
    @SerialName("id")
    val id: Int?,
    @SerialName("link")
    val link: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("order")
    val order: Int?,
    @SerialName("visible")
    val visible: Int?
)