package com.example.newsapp.api.model

import java.io.Serializable

data class SourcesResponse(
    var status: String?,
    var sources: List<Source>?
)

data class Source(
    var id: String?,
    var name: String?,
    var description: String?,
    var url: String?,
    var category: String?,
    var language: String?,
    var country: String?
): Serializable