package com.example.mymeal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymeal.model.OrderViewModel
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

class ViewModelTest {
    //Specify that LiveData objects should not call the main thread
    @get : Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun check_subtotal() {
        //Create an instance of OrderViewModel
        val viewModel = OrderViewModel()

        //Observing the LiveData object
        viewModel.subtotal.observeForever { }

        //Set the dessert
        viewModel.setDessert("luqaimat")

        //Set the drink
        viewModel.setDrink("osha")

        //Set the salad
        viewModel.setSalad("fattoush")

        //Set the main meal
        viewModel.setMeal("charcoal grilled chicken")


        //Comparing the actual value with the expecting value
        assertEquals("$122.90", viewModel.subtotal.value)
    }


    @Test
    fun main_meal_price() {
        //Create an instance of OrderViewModel
        val viewModel = OrderViewModel()

        //Observing the LiveData object
        viewModel.mainMeal.observeForever { }

        //Set the main meal
        viewModel.setMeal("royal butter lobster")

        //Comparing the actual value with the expecting value
        assertEquals("99.9", viewModel.mainMeal.value?.price.toString())
    }

    @Test
    fun salad_price() {
        //Create an instance of OrderViewModel
        val viewModel = OrderViewModel()

        //Observing the LiveData object
        viewModel.salad.observeForever { }

        //Set the salad
        viewModel.setSalad("jarjeer")

        //Comparing the actual value with the expecting value
        assertEquals("22.5", viewModel.salad.value?.price.toString())
    }

    @Test
    fun drink_price() {
        //Create an instance of OrderViewModel
        val viewModel = OrderViewModel()

        //Observing the LiveData object
        viewModel.drink.observeForever { }

        //Set the drink
        viewModel.setDrink("rabdan")

        //Comparing the actual value with the expecting value
        assertEquals("27.5", viewModel.drink.value?.price.toString())
    }

    @Test
    fun dessert_price() {
        //Create an instance of OrderViewModel
        val viewModel = OrderViewModel()

        //Observing the LiveData object
        viewModel.dessert.observeForever { }

        //Set the dessert
        viewModel.setDessert("Kunafa")

        //Comparing the actual value with the expecting value
        assertEquals("42.5", viewModel.dessert.value?.price.toString())
    }
}