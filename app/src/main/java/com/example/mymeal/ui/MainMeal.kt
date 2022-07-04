package com.example.mymeal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymeal.R
import com.example.mymeal.databinding.FragmentMainMealBinding
import com.example.mymeal.model.OrderViewModel


class MainMeal : Fragment() {

    // Binding object instance corresponding to the fragment_main_meal.xml layout.
    private var _binding: FragmentMainMealBinding? = null

    private val binding get() = _binding!!

    //Get instance of OrderViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        // add viewModel + fragmentMainMeal
    }

    //Navigate to the salad menu fragment
    fun goToNextFragment() {
        findNavController().navigate(R.id.action_mainMeal_to_saladMenu)
    }

    //Cancel the order
    fun cancelOrder() {
        //Reset the values
        sharedViewModel.resetOrder()
        //Navigate back to the start fragment
        findNavController().navigate(R.id.action_mainMeal_to_startFragment)
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