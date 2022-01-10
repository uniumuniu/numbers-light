package com.example.numberslight.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.numberslight.NumbersApplication
import com.example.numberslight.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var numbersListFragment: Fragment
    private lateinit var numberDetailFragment: Fragment
    private lateinit var noConnectionFragment: Fragment
    private var fragmentsVisibility: FragmentsVisibility = FragmentsVisibility.LIST

    private val viewModel: NumbersViewModel by viewModels {
        (applicationContext as NumbersApplication).appComponent.viewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        numbersListFragment = supportFragmentManager.findFragmentById(R.id.numbersListFragment)!!
        numberDetailFragment = supportFragmentManager.findFragmentById(R.id.numberDetailFragment)!!
        noConnectionFragment = supportFragmentManager.findFragmentById(R.id.noConnectionFragment)!!

        showProperView()

        viewModel.selectedNumber.observe(this, {
            showProperView()
        })

        viewModel.error.observe(this, {
            showProperView()
        })
    }

    private fun showProperView() {
        when {
            viewModel.error.value != null -> {
                showError()
            }
            viewModel.selectedNumber.value != null -> {
                showDetails()
            }
            else -> {
                showList()
            }
        }
    }

    private fun showDetails() {
        val transaction = supportFragmentManager.beginTransaction()
        hideNumbersListFragment(transaction)
        showNumberDetailFragment(transaction)
        hideNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.DETAILS
    }

    private fun showList() {
        val transaction = supportFragmentManager.beginTransaction()
        showNumbersListFragment(transaction)
        hideNumberDetailFragment(transaction)
        hideNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.LIST
    }

    private fun showError() {
        val transaction = supportFragmentManager.beginTransaction()
        hideNumbersListFragment(transaction)
        hideNumberDetailFragment(transaction)
        showNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.ERROR
    }

    override fun onBackPressed() {
        if (fragmentsVisibility == FragmentsVisibility.DETAILS) {
            viewModel.setSelectedNumber(null)
            showList()
        } else {
            super.onBackPressed()
        }
    }

    private fun showNumbersListFragment(transaction: FragmentTransaction) {
        transaction.show(numbersListFragment)
    }

    private fun hideNumbersListFragment(transaction: FragmentTransaction) {
        transaction.hide(numbersListFragment)
    }

    private fun showNumberDetailFragment(transaction: FragmentTransaction) {
        transaction.show(numberDetailFragment)
    }

    private fun hideNumberDetailFragment(transaction: FragmentTransaction) {
        transaction.hide(numberDetailFragment)
    }

    private fun showNoConnectionFragment(transaction: FragmentTransaction) {
        transaction.show(noConnectionFragment)
    }

    private fun hideNoConnectionFragment(transaction: FragmentTransaction) {
        transaction.hide(noConnectionFragment)
    }
}
