package com.am.recipe.presentation.model

import android.os.Environment
import com.am.recipe.domain.model.Recipe
import com.am.recipe.util.Util
import java.io.File
import java.util.Date

class HTMLGen(private val recipe: Recipe) {

    fun exportRecipe(): Boolean{
        val path = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString()
        return try { createFile(path) } catch (ignored: Exception){ false }
    }

    private fun createFile(path: String): Boolean {
        val fileName = "recipe.${recipe.title}.${Date().time}.html"
        File(path, fileName).printWriter().use { it.println(htmlString()) }
        return true
    }

    private fun htmlString() = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Recipe: ${recipe.title}</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f7f7f7;
                    color: #333;
                    margin: 0;
                    padding: 20px;
                }
                .recipe-container {
                    max-width: 800px;
                    margin: 0 auto;
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                h1 {
                    font-size: 2.5rem;
                    color: #2c3e50;
                }
                .meta {
                    color: #7f8c8d;
                    font-size: 1rem;
                }
                .category {
                    background-color: #3498db;
                    color: white;
                    padding: 5px 10px;
                    border-radius: 5px;
                    display: inline-block;
                }
                .thumb {
                    width: 100%;
                    border-radius: 10px;
                    margin-bottom: 20px;
                }
                .description {
                    font-size: 1.2rem;
                    line-height: 1.6;
                    margin-bottom: 20px;
                }
                .ingredients {
                    list-style-type: disc;
                    padding-left: 20px;
                }
                .links a {
                    color: #e74c3c;
                    text-decoration: none;
                    margin-right: 10px;
                }
                .links a:hover {
                    text-decoration: underline;
                }
                .footer {
                    margin-top: 20px;
                    font-size: 0.9rem;
                    color: #95a5a6;
                }
            </style>
        </head>
        <body>

        <div class="recipe-container">
            <h1>${recipe.title}</h1>
            <p class="meta">Category: <span class="category">${recipe.category}</span> | Area: ${recipe.area}</p>

            <img class="thumb" src="${recipe.thumb}" alt="${recipe.title}">

            <p class="description">${recipe.description}</p>

            <h2>Ingredients and Measurements:</h2>
            <ul class="ingredients">
                ${ingredientHtmlList()}
            </ul>

            <div class="links">
                <a href="${Util.getYoutubeLink(recipe.youtubeLink, recipe.title)}" target="_blank">Watch</a>
                <a href="${Util.getGoogleLink(recipe.sourceLink, recipe.title)}" target="_blank">View on web</a>
            </div>

            <p class="footer">Last modified: ${recipe.dateModified ?: "unknown"}</p>
        </div>

        </body>
        </html>
    """.trimIndent()

    private fun ingredientHtmlList() =
        recipe.ingredientMeasureList.joinToString {
            "<li>${it.replace('#', ' ')}</li>\n"
        }


}