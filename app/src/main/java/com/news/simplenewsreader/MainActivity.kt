package com.news.simplenewsreader

import Article
import NewsResponse
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    
    //클래스 내에서 사용할 변수들
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var articles: List<Article> = listOf()
    private var displayedArticles: MutableList<Article> = mutableListOf()
    private var currentPosition: Int = 0
    private val pageSize: Int = 5
    
    //액티비티가 생성될 때 호출되는 메소드입니다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //RecyclerView 설정
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //뉴스 데이터를 받아와서 어댑터에 설정합니다
        //RetrofitInstance 에도 적었지만, newsAPI.org 의 무료 키는 12시간에 50번밖에 못 씁니다.
        newsAdapter = NewsAdapter(displayedArticles) { article ->
            showNewsSummaryDialog(article)
        }
        recyclerView.adapter = newsAdapter

        val refreshButton: Button = findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener {
            refreshNews()
        }

        val loadMoreButton: Button = findViewById(R.id.load_more_button)
        loadMoreButton.setOnClickListener {
            loadMoreArticles()
        }
        //커스텀 Spinner 디자인으로 기존 Spinner 를 대체하고 어댑터에 추가
        val spinner: Spinner = findViewById(R.id.category_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.news_categories,
            R.layout.custom_spinner_item // 내가 만든 spinner 디자인
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        //Spinner 카테고리 선택 관련 이벤트
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val category = getCategoryFromPosition(position)
                fetchNews(category)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    //화면에 보이는건 한글로 하지만, 사용자가 입력했을 때 value 를 반환
    private fun getCategoryFromPosition(position: Int): String {
        return when (position) {
            0 -> "business"
            1 -> "entertainment"
            2 -> "general"
            3 -> "health"
            4 -> "science"
            5 -> "sports"
            6 -> "technology"
            else -> "general"
        }
    }
    //뉴스 데이터를 가져오는 메소드
    private fun fetchNews(category: String) {
        RetrofitInstance.api.getTopHeadlines(category).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    articles = response.body()!!.articles
                    currentPosition = 0
                    displayedArticles.clear()
                    loadMoreArticles()
                    Log.d("MainActivity", "뉴스 불러오기 성공")
                } else {
                    //API 오류 관련 내용이 상세히 뜨니 logCat 확인
                    Log.e("MainActivity", "뉴스 불러오기 실패: ${response.message()}")
                    Log.e("MainActivity", "실패 반응 코드: ${response.code()}")
                    Log.e("MainActivity", "세부 실패 내용: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "뉴스를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    //뉴스 새로고침 해주는 기능
    private fun refreshNews() {
        val category = getCategoryFromPosition(findViewById<Spinner>(R.id.category_spinner).selectedItemPosition)
        fetchNews(category)
        Toast.makeText(this, "뉴스가 새로고침되었습니다.", Toast.LENGTH_SHORT).show()
    }
    //API가 제공하는 헤드라인의 더보기
    private fun loadMoreArticles() {
        val nextPosition = currentPosition + pageSize
        if (currentPosition < articles.size) {
            val nextArticles = articles.subList(currentPosition, nextPosition.coerceAtMost(articles.size))
            displayedArticles.addAll(nextArticles)
            newsAdapter.notifyDataSetChanged()
            currentPosition = nextPosition
        }
    }
    //xml에 있는 Dialog 를 호출
    //Dialog에 닫기와 더보기 버튼 기능 부여
    //Dialog의 요소에 해당하는 값들을 배정
    private fun showNewsSummaryDialog(article: Article) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_news_summary, null)
        val titleTextView: TextView = dialogView.findViewById(R.id.tvTitle)
        val descriptionTextView: TextView = dialogView.findViewById(R.id.tvDescription)
        val readMoreButton: Button = dialogView.findViewById(R.id.btnReadMore)
        val closeButton: Button = dialogView.findViewById(R.id.btnClose)

        titleTextView.text = article.title
        descriptionTextView.text = article.description

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        readMoreButton.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
            intent.putExtra("url", article.url)
            startActivity(intent)
        }

        dialog.show()
    }
}