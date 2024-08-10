package com.example.cryptocurrencyassignment.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyassignment.Model.Coin
import com.example.cryptocurrencyassignment.Repository.CoinRepository
import kotlinx.coroutines.launch

class CoinViewModel(private val repository: CoinRepository) : ViewModel() {

    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> = _coins

    private val _coinDetails = MutableLiveData<Coin>()
    val coinDetails: LiveData<Coin> = _coinDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadCoins() {
        _loading.value = true
        viewModelScope.launch {
            val coinList = repository.fetchCoins()
            _coins.value = coinList
            _loading.value = false
        }
    }

    fun searchCoins(query: String) {
        val filteredList = _coins.value?.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.symbol.contains(query, ignoreCase = true)
        }
        _coins.value = filteredList!!
    }
    fun fetchCoinDetails(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val coin = repository.getCoinDetails(id)
                _coinDetails.value = coin
            } catch (e: Exception) {
                _error.value = "Error fetching details"
            }
            _loading.value = false
        }
    }
}

