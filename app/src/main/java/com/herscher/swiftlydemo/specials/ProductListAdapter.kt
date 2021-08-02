package com.herscher.swiftlydemo.specials

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.herscher.swiftlydemo.R
import com.herscher.swiftlydemo.databinding.ViewProductSaleBinding
import com.herscher.swiftlydemo.util.GenericDiffUtilCallback
import com.squareup.picasso.Picasso

class ProductListAdapter(
    context: Context
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var productList = emptyList<Product>()
    private var screenWidth = 0

    fun update(newProductList: List<Product>, screenWidth: Int) {
        // If screen width is different we will have to redraw everything
        if (this.screenWidth != screenWidth) {
            this.screenWidth = screenWidth
            productList = newProductList
            notifyDataSetChanged()
        } else {
            // In this case detect only changed items in the list
            val diff =
                DiffUtil.calculateDiff(GenericDiffUtilCallback(productList, newProductList), true)
            productList = newProductList
            diff.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewProductSaleBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.apply {
            name.text = product.name
            currentPrice.text = product.currentPrice
            originalPrice.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                text = product.originalPrice
            }

            Picasso.get().load(product.imageUrl).placeholder(R.drawable.ic_error).into(photo)
        }

        // Adjust size of the view
        holder.itemView.apply {
            layoutParams.height = (product.dimension.heightPercentage * screenWidth).toInt()
            layoutParams.width = (product.dimension.widthPercentage * screenWidth).toInt()
        }
    }

    override fun getItemCount() = productList.size

    class ViewHolder(val binding: ViewProductSaleBinding) :
        RecyclerView.ViewHolder(binding.root)
}