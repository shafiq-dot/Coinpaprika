package com.example.cryptocurrencyassignment.Activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocurrencyassignment.Model.Coin
import com.example.cryptocurrencyassignment.R
import com.example.cryptocurrencyassignment.Repository.CoinRepository
import com.example.cryptocurrencyassignment.Retrofit.CoinPaprikaApi
import com.example.cryptocurrencyassignment.ViewModel.CoinViewModel
import com.example.cryptocurrencyassignment.ViewModel.CoinViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinDetailsActivity : AppCompatActivity() {


    private val viewModel: CoinViewModel by viewModels {
        CoinViewModelFactory(CoinRepository(getDetail()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
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


        val coinId = intent.getStringExtra("coin_id")
        coinId?.let { viewModel.fetchCoinDetails(it) }

        viewModel.coinDetails.observe(this, { coin ->
            findViewById<TextView>(R.id.name_text_view).text = coin.name
            findViewById<TextView>(R.id.symbol_text_view).text = coin.symbol
            findViewById<TextView>(R.id.price_text_view).text =
                "Price: ${coin.rank ?: "N/A"} RANK"
            // Bind other coin details
        })

        viewModel.error.observe(this, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }


    private fun getDetail(): CoinPaprikaApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CoinPaprikaApi::class.java)
    }


}
