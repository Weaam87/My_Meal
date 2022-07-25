package com.example.mymeal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymeal.OrderApplication
import com.example.mymeal.R
import com.example.mymeal.databinding.FragmentDrinksMenuBinding
import com.example.mymeal.model.OrderViewModel
import com.example.mymeal.model.OrderViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DrinksMenu : Fragment() {

    // Binding object instance corresponding to the fragment_drinks_menu.xml layout.
    private var _binding: FragmentDrinksMenuBinding? = null

    private val binding get() = _binding!!

    //Get instance of OrderViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels() {
        OrderViewModelFactory((activity?.application as OrderApplication).database.orderDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrinksMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel
        binding.drinkMenu = this
    }

    //Navigate to the desserts menu fragment if _drink.value is not nul
    fun goToNextFragment() {
        if (sharedViewModel.hasNoDrink()) {
            Toast.makeText(context, R.string.select_drink, Toast.LENGTH_SHORT).show()
        } else {
            findNavController().navigate(R.id.action_drinksMenu_to_dessertsMenu)
        }
    }

    //Cancel the order
    private fun cancelOrder() {
        //Reset the values
        sharedViewModel.resetOrder()
        //Navigate back to the start fragment
        findNavController().navigate(R.id.action_drinksMenu_to_startFragment)
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