package com.asad.currencyapp.ui.currencyrate.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import java.util.*

class CurrencyCountrySpinnerAdapter(
    context: Context, textViewResourceId: Int
) : ArrayAdapter<CurrencyNames>(context, textViewResourceId) {

    private val values: MutableList<CurrencyNames> = ArrayList()

    fun updateItems(list: List<CurrencyNames>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): CurrencyNames {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].currencyCountryName
        return label
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = values[position].currencyCountryName
        return label
    }
}