package com.example.new_application.adapter

import com.example.new_application.retrofit.response.Article

interface AdapterClickListener {
     fun clickListener(article: Article)
}