package com.example.mymeal.model


import androidx.lifecycle.*
import com.example.mymeal.data.DataSource
import com.example.mymeal.data.OrderDao
import com.example.mymeal.data.OrderDetails
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


private const val PRICE_FOR_SAME_DAY = 5.00

class OrderViewModel(private val orderDao: OrderDao) : ViewModel() {

    // Get all orders from the database
    val allOrders : LiveData<List<OrderDetails>> = orderDao.getAllOrders().asLiveData()

    // Map of food items
    val menuItems = DataSource.menuItems

    // Default values for item prices
    private var previousMealPrice = 0.0
    private var previousSaladPrice = 0.0
    private var previousDrinkPrice = 0.0
    private var previousDessertPrice = 0.0

    private val taxRate = 0.06

    //List of the next 7 days
    val dateOptions = getBookingDateOptions()

    // Main meal for the order
    private val _mainMeal = MutableLiveData<MenuItem?>()
    val mainMeal: LiveData<MenuItem?> = _mainMeal

    // Salad for the order
    private val _salad = MutableLiveData<MenuItem?>()
    val salad: LiveData<MenuItem?> = _salad

    // Drink for the order
    private val _drink = MutableLiveData<MenuItem?>()
    val drink: LiveData<MenuItem?> = _drink

    // Dessert for the order
    private val _dessert = MutableLiveData<MenuItem?>()
    val dessert: LiveData<MenuItem?> = _dessert

    // Date of booking
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // Subtotal for the order
    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = Transformations.map(_subtotal) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Total for the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        resetOrder()
    }

    // Add new order to the database using coroutine
    private fun insertOrder(orderDetails: OrderDetails) {
        viewModelScope.launch { orderDao.insert(orderDetails) }
    }

    // Delete an order from the database using coroutine
    fun deleteOrder(orderDetails: OrderDetails) {
        viewModelScope.launch { orderDao.delete(orderDetails) }
    }

    private fun getNewOrderEntry(
        mainMeal : String,
        salad : String,
        drink : String,
        dessert : String,
        bookingDate : String,
        total : String
    ) : OrderDetails {
        return OrderDetails(
            mainMeal = mainMeal,
            salad = salad,
            drink = drink,
            dessert = dessert,
            bookingDate = bookingDate,
            total = total
        )
    }

    fun addNewOrder(
        mainMeal : String,
        salad : String,
        drink : String,
        dessert : String,
        bookingDate : String,
        total : String
    ) {
        val newOrder = getNewOrderEntry(mainMeal,salad,drink,dessert,bookingDate,total)
        insertOrder(newOrder)
    }

    // Add meal for order
    fun setMeal(meal: String) {

        //if mainMeal value is not null,set the previous meal price to the current meal price
        previousMealPrice = _mainMeal.value?.price ?: 0.0

        // This line to ensures that we only charge for the currently selected meal,
        //in case if the users changed their mind
        _subtotal.value = (_subtotal.value ?: 0.0) - previousMealPrice

        _mainMeal.value = menuItems[meal]

        //reflect the price of the selected meal
        updateSubtotal(menuItems[meal]?.price ?: 0.0)
    }

    //Add salad for order
    fun setSalad(salad: String) {
        //if salad value is not null,set the previous salad price to the current salad price
        previousSaladPrice = _salad.value?.price ?: 0.0

        // This line to ensures that we only charge for the currently selected salad,
        //in case if the users changed their mind
        _subtotal.value = (_subtotal.value ?: 0.0) - previousSaladPrice

        _salad.value = menuItems[salad]

        //reflect the price of the selected salad
        updateSubtotal(menuItems[salad]?.price ?: 0.0)
    }

    //Add drink for order
    fun setDrink(drink: String) {
        //if drink value is not null,set the previous drink price to the current drink price
        previousDrinkPrice = _drink.value?.price ?: 0.0

        // This line to ensures that we only charge for the currently selected drink,
        //in case if the users changed their mind
        _subtotal.value = (_subtotal.value ?: 0.0) - previousDrinkPrice

        _drink.value = menuItems[drink]

        //reflect the price of the selected drink
        updateSubtotal(menuItems[drink]?.price ?: 0.0)
    }

    //Add dessert for order
    fun setDessert(dessert: String) {
        //if dessert value is not null,set the previous dessert price to the current dessert price
        previousDessertPrice = _dessert.value?.price ?: 0.0

        // This line to ensures that we only charge for the currently selected dessert,
        //in case if the users changed their mind
        _subtotal.value = (_subtotal.value ?: 0.0) - previousDessertPrice

        _dessert.value = menuItems[dessert]

        //reflect the price of the selected dessert
        updateSubtotal(menuItems[dessert]?.price ?: 0.0)
    }

    fun setDate(bookingDate: String) {
        _date.value = bookingDate
        updatePriceForSameDay()
    }

    // Calculate subtotal
    private fun updateSubtotal(itemPrice: Double) {

        if (_subtotal.value != null) {
            _subtotal.value = (_subtotal.value)?.plus(itemPrice)
        } else {
            _subtotal.value = itemPrice
        }
        calculateTaxAndTotal()
    }

    //Calculate tax and total
    fun calculateTaxAndTotal() {

        _tax.value = _subtotal.value?.times(taxRate)

        _total.value = _tax.value?.plus(_subtotal.value!!)
    }

    //Create a List for the next 7 days
    private fun getBookingDateOptions(): List<String> {
        val options = mutableListOf<String>()
        //Create formatter string
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        //Get a Calendar instance
        val calender = Calendar.getInstance()
        //Get the next seven days dates
        repeat(7) {
            options.add(formatter.format(calender.time))
            calender.add(Calendar.DATE, 1)
        }
        return options
    }

    //Charge extra for the same day booking
    private fun updatePriceForSameDay() {
        var calculatedPrice = (_mainMeal.value?.price ?: 0.0) + (_salad.value?.price ?: 0.0) +
                (_drink.value?.price?:0.0) + (_dessert.value?.price ?: 0.0)
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY
        }
        _subtotal.value = calculatedPrice
    }

    //Reset all values
    fun resetOrder() {
        _total.value = 0.0
        _tax.value = 0.0
        _subtotal.value = 0.0
        _mainMeal.value = null
        _salad.value = null
        _drink.value = null
        _dessert.value = null
        _date.value = dateOptions[1]
        previousMealPrice = 0.0
        previousSaladPrice = 0.0
        previousDrinkPrice = 0.0
        previousDessertPrice = 0.0
    }

    //Check if _mainMeal.value is null
    fun hasNoMeal() : Boolean {
        return _mainMeal.value == null
    }

    //Check if _salad.value is null
    fun hasNoSalad() : Boolean {
        return _salad.value == null
    }

    //Check if _drink.value is null
    fun hasNoDrink() : Boolean {
        return _drink.value == null
    }

    //Check if _dessert.value is null
    fun hasNoDessert() : Boolean {
        return _dessert.value == null
    }

    //Check if the booking is in the same day that the order was made
    fun notSameDayOrder() : Boolean {
        return _date.value != dateOptions[0]
    }

}

class OrderViewModelFactory(private val orderDao: OrderDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(orderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}