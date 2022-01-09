package com.example.numberslight.presentation

import android.content.res.Configuration
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
                showErrorOnly()
            }
            viewModel.selectedNumber.value != null && isTabletAndLandscape() -> {
                showListWithDetails()
            }
            viewModel.selectedNumber.value != null -> {
                showDetailsOnly()
            }
            else -> {
                showListOnly()
            }
        }
    }

    private fun showDetailsOnly() {
        val transaction = supportFragmentManager.beginTransaction()
        hideNumbersListFragment(transaction)
        showNumberDetailFragment(transaction)
        hideNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.DETAILS
    }

    private fun showListOnly() {
        val transaction = supportFragmentManager.beginTransaction()
        showNumbersListFragment(transaction)
        hideNumberDetailFragment(transaction)
        hideNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.LIST
    }

    private fun showListWithDetails() {
        val transaction = supportFragmentManager.beginTransaction()
        showNumbersListFragment(transaction)
        showNumberDetailFragment(transaction)
        hideNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.LIST_AND_DETAILS
    }

    private fun showErrorOnly() {
        val transaction = supportFragmentManager.beginTransaction()
        hideNumbersListFragment(transaction)
        hideNumberDetailFragment(transaction)
        showNoConnectionFragment(transaction)
        transaction.commit()
        fragmentsVisibility = FragmentsVisibility.ERROR
    }

    override fun onBackPressed() {
        if (fragmentsVisibility == FragmentsVisibility.DETAILS
            || (fragmentsVisibility == FragmentsVisibility.LIST_AND_DETAILS && isTabletAndLandscape())) {
            viewModel.setSelectedNumber(null)
            showListOnly()
        } else {
            super.onBackPressed()
        }
    }

    private fun isTablet(): Boolean {
        return ((resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun isTabletAndLandscape(): Boolean {
        return isTablet() && isLandscape()
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
