data class NewsResponse(val articles: List<Article>)

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    /*페이징을 하고 싶은데 못함*/
    val page: String,
    val pageSize: String
)