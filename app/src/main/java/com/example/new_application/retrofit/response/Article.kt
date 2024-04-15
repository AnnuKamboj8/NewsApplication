package com.example.new_application.retrofit.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize



@Parcelize
@Entity(tableName = "article", indices =[Index(value = ["title"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    var title: String? = null,
    val url: String?,
    val urlToImage: String?
): Parcelable
