package com.example.mymeal.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymeal.OrderApplication
import com.example.mymeal.R
import com.example.mymeal.databinding.FragmentOrderSummaryBinding
import com.example.mymeal.model.OrderViewModel
import com.example.mymeal.model.OrderViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class OrderSummary : Fragment() {

    // Binding object instance corresponding to the fragment_order_summary.xml layout.
    private var _binding: FragmentOrderSummaryBinding? = null

    private val binding get() = _binding!!

    //Get instance of OrderViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels() {
        OrderViewModelFactory((activity?.application as OrderApplication).database.orderDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderSummaryBinding.inflate(inflater, container, false)
        val root = binding.root

        // Calculate tax and total
        sharedViewModel.calculateTaxAndTotal()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel
        binding.orderSummaryFragment = this

        //Hide the extra charge text if the booking is not in the same day
        if (sharedViewModel.notSameDayOrder()) {
            binding.sameDayBooking.setVisibility(View.GONE)
            binding.sameDayBookingCharge.setVisibility(View.GONE)
            binding.divider4.setVisibility(View.GONE)
        }
    }

    // get the order details to add it to the database
    private fun addNewOrder() {
        sharedViewModel.addNewOrder(
            sharedViewModel.mainMeal.value?.name.toString(),
            sharedViewModel.salad.value?.name.toString(),
            sharedViewModel.drink.value?.name.toString(),
            sharedViewModel.dessert.value?.name.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.total.value.toString()
        )
    }

    //Cancel the order
    private fun cancelOrder() {
        //Reset the values
        sharedViewModel.resetOrder()
        //Navigate back to the start fragment
        findNavController().navigate(R.id.action_orderSummary_to_startFragment)
    }

    fun submitOrder() {
        // add the order to the database
        addNewOrder()

        val orderSummary = getString(R.string.order_details,
            sharedViewModel.mainMeal.value?.name.toString(),
            sharedViewModel.salad.value?.name.toString(),
            sharedViewModel.drink.value?.name.toString(),
            sharedViewModel.dessert.value?.name.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.total.value.toString()
        )
        val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)

        //This check will prevent the app from crashing if there's no app to handle the intent
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }

    private fun doNothing() {
        return
    }

    //confirmation before cancel the order
    fun showDialog() {
        MaterialAlertDialogBuilder(requireContext()).setMessage(getString(R.string.cancel_order))
            .setCancelable(true)
            .setNegativeButton(getString(R.string.yes)) { _, _ -> cancelOrder() }
            .setPositiveButton(getString(R.string.no)) { _, _ -> doNothing() }.show()
    }

    /**
    Clear out the binding object when the view hierarchy associated with the fragment
    is being removed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}