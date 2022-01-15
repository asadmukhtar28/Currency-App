package com.asad.currencyapp.ui.currencyrate

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.asad.currencyapp.BR
import com.asad.currencyapp.R
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.databinding.ActivityCurrencyRateListBinding
import com.asad.currencyapp.ui.base.BaseActivity
import com.asad.currencyapp.ui.currencyrate.adapter.CurrencyCountrySpinnerAdapter
import com.asad.currencyapp.ui.currencyrate.adapter.CurrencyRatesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyRateActivity :
    BaseActivity<CurrencyRateViewModel, ActivityCurrencyRateListBinding>(R.layout.activity_currency_rate_list) {

    @Inject
    lateinit var adapter: CurrencyRatesAdapter
    private val spinnerAdapter by lazy {
        CurrencyCountrySpinnerAdapter(context, android.R.layout.simple_spinner_item)
    }
    lateinit var currencyItem: CurrencyNames

    private val spinnerClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            currencyItem = parent.getItemAtPosition(position) as CurrencyNames
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }

    }

    override val viewModel: CurrencyRateViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel

    override fun initUi() {
        viewBinding.rvCurrencies.adapter = adapter
        viewBinding.spinner.adapter = spinnerAdapter
        viewBinding.spinner.onItemSelectedListener = spinnerClickListener

        setUpObserver()
    }

    private fun setUpObserver() {

        viewModel.currencyExchangeRateList.observe(this, {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        })
        viewModel.currencyExchangeRate.observe(this, {
            if (it.isNotEmpty()) {
                adapter.updateList(it)
            }
        })
        viewModel.currencyNameList.observe(this, { list ->
            if (list.isNotEmpty()) {
                spinnerAdapter.updateItems(list)
            }
        })

        viewBinding.editTextNumberDecimal.doAfterTextChanged {
            if (this::currencyItem.isInitialized) {
                viewModel.exchangeCurrencyQuotes(
                    currencyItem.currencyName,
                    viewBinding.editTextNumberDecimal.text.toString().toDouble()
                )
            } else {
                Toast.makeText(
                    context,
                    "Please select Country from Top Drop Down",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}