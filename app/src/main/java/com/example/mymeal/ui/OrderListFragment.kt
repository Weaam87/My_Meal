package com.example.mymeal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymeal.OrderApplication
import com.example.mymeal.OrderListAdapter
import com.example.mymeal.databinding.FragmentOrderListBinding
import com.example.mymeal.model.OrderViewModel
import com.example.mymeal.model.OrderViewModelFactory


class OrderListFragment : Fragment() {

    //Get instance of OrderViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels() {
        OrderViewModelFactory((activity?.application as OrderApplication).database.orderDao())
    }

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OrderListAdapter {
            //Add a click handler when click on the item
            //val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            //this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        // Attach an observer on the allOrders to listen for the data changes.
        // This will update the RecyclerView with the new items on the list.
        sharedViewModel.allOrders.observe(this.viewLifecycleOwner) {
            orders -> orders.let { adapter.submitList(it) }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

}