package com.example.mymeal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymeal.data.OrderDetails
import com.example.mymeal.databinding.OrderListBinding


class OrderListAdapter(private val onOrderClicked : (OrderDetails) -> Unit) :
ListAdapter<OrderDetails , OrderListAdapter.OrderViewHoled>(DiffCallback) {

    class OrderViewHoled(private var binding : OrderListBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(orderDetails: OrderDetails) {
            binding.mainMeal.text = orderDetails.mainMeal
            binding.salad.text = orderDetails.salad
            binding.drink.text = orderDetails.drink
            binding.dessert.text = orderDetails.dessert
            binding.bookingDate.text = orderDetails.bookingDate
            binding.totalString.text = orderDetails.total
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHoled {
        val viewHolder = OrderViewHoled(
            OrderListBinding.inflate(LayoutInflater.from(parent.context))
        )
        //set the onClickListener() to call onOrderClicked() for the item at the current position.
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onOrderClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: OrderViewHoled, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<OrderDetails>() {
            //checks if the object "row"  is the same by only checking the ID
            override fun areItemsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
                return oldItem.id == newItem.id
            }

            //checks if all properties, not just the ID, are the same
            override fun areContentsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
                return oldItem == newItem
            }

        }

    }
}