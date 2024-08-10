package com.example.cryptocurrencyassignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyassignment.Model.Coin

class CoinAdapter(private var coins: List<Coin>, private val onItemClick: (Coin) -> Unit) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    fun updateData(newCoins: List<Coin>) {
        coins = newCoins
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = coins[position]
        holder.bind(coin)
    }

    override fun getItemCount(): Int = coins.size

    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(android.R.id.text1)
        private val symbolTextView: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(coin: Coin) {
            nameTextView.text = coin.name
            symbolTextView.text = coin.symbol
            itemView.setOnClickListener { onItemClick(coin) }
        }
    }
}
