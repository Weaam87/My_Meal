package com.example.mymeal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymeal.R
import com.example.mymeal.databinding.FragmentStartBinding


class StartFragment : Fragment() {

   // Binding object instance corresponding to the fragment_start.xml layout.
   private var _binding : FragmentStartBinding? = null

   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = FragmentStartBinding.inflate(inflater,container,false)
      val root : View = binding.root

      binding.startOrderButton.setOnClickListener {
         findNavController().navigate(R.id.action_startFragment_to_mainMeal)
      }
      return root
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