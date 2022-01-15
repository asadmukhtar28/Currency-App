package com.asad.currencyapp.ui.currencyrate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.databinding.ItemCurrencyQuotesBinding
import com.asad.currencyapp.ui.base.BaseViewHolder
import javax.inject.Inject

class CurrencyRatesAdapter @Inject constructor() :
    RecyclerView.Adapter<CurrencyRatesAdapter.ArticleItemViewHolder>() {

    fun updateList(articleList: List<CurrencyRates>) {
        this.articleList.clear()
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    var articleList: MutableList<CurrencyRates> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        return ArticleItemViewHolder(
            ItemCurrencyQuotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ArticleItemViewHolder(private val binding: ItemCurrencyQuotesBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            binding.viewModel = CurrencyRatesItemViewModel(articleList[position])
            binding.executePendingBindings()
        }

    }
}