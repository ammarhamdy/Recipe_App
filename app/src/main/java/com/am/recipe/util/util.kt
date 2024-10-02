package com.am.recipe.util

object Util {


    fun getGoogleLink(link: String?, searchKey: String): String =
        link?.let { l ->
            l.ifEmpty {
                "https://www.google.com/search?q=" + searchKey
                    .filter { it.isLetter() || it.isWhitespace() }
                    .replace(' ', '+')
            }
        } ?: (
                "https://www.google.com/search?q=" + searchKey
                    .filter { it.isLetter() || it.isWhitespace() }
                    .replace(' ', '+')
                )

    fun getYoutubeLink(link: String?, searchKey: String): String =
        link?.let { l ->
            l.ifEmpty {
                "https://www.youtube.com/results?search_query=" + searchKey
                    .filter { it.isLetter() || it.isWhitespace() }
                    .replace(' ', '+')
            }
        } ?: (
                "https://www.youtube.com/results?search_query=" + searchKey
                    .filter { it.isLetter() || it.isWhitespace() }
                    .replace(' ', '+')
                )

}