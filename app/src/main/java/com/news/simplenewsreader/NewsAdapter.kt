package com.news.simplenewsreader

import Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private var articles: List<Article>, private val onItemClick: (Article) -> Unit) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]

        holder.title.text = article.title
        holder.date.text = article.publishedAt?.substring(0, 10) // 날짜 갖고오기

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.default_image) // 기본 이미지 설정
            .error(R.drawable.default_image) // 오류 시 기본 이미지 설정
            .into(holder.image)
        holder.itemView.setOnClickListener { onItemClick(article) }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.article_title)
        val date: TextView = itemView.findViewById(R.id.article_date)
        val image: ImageView = itemView.findViewById(R.id.article_image)
    }


}