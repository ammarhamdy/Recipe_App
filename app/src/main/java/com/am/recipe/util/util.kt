package com.am.recipe.util

object Util {


    fun getGoogleLink(link: String?, searchKey: String) = link
        ?: ("https://www.google.com/search?q=" + searchKey
            .filter { it.isLetter() || it.isWhitespace() }
            .replace(' ', '+'))

    fun getYoutubeLink(link: String?, searchKey: String) = link
        ?: ("https://www.youtube.com/results?search_query=" + searchKey
            .filter { it.isLetter() || it.isWhitespace() }
            .replace(' ', '+'))

}