package com.example.cryptocurrencyassignment.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cryptocurrencyassignment.Adapter.CoinAdapter
import com.example.cryptocurrencyassignment.R
import com.example.cryptocurrencyassignment.Repository.CoinRepository
import com.example.cryptocurrencyassignment.Retrofit.CoinPaprikaApi
import com.example.cryptocurrencyassignment.ViewModel.CoinViewModel
import com.example.cryptocurrencyassignment.ViewModel.CoinViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {



    private lateinit var adapter: CoinAdapter
    private val viewModel: CoinViewModel by viewModels {
        CoinViewModelFactory(CoinRepository(createApi()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val actionBar: ActionBar? = supportActionBar


        // providing title for the ActionBar
        actionBar?.title = "  Coinpaprika"


        // providing subtitle for the ActionBar
        actionBar?.subtitle = "   All about cryptocurrency"


        // adding icon in the ActionBar
        actionBar?.setIcon(R.drawable.ic_action_name)


        // methods to display the icon in the ActionBar
        actionBar?.setDisplayUseLogoEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        val searchView = findViewById<SearchView>(R.id.searchView)

        adapter = CoinAdapter(emptyList()) { coin ->
            val intent = Intent(this, CoinDetailsActivity::class.java).apply {
                putExtra("coin_id", coin.id)
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadCoins()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchCoins(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchCoins(newText ?: "")
                return true
            }
        })

        viewModel.coins.observe(this, Observer { coins ->
            adapter.updateData(coins)
            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
        })

        // Load the initial coin data
        viewModel.loadCoins()
    }

    private fun createApi(): CoinPaprikaApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CoinPaprikaApi::class.java)
    }
}