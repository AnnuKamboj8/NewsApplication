package com.example.new_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.new_application.R
import com.example.new_application.retrofit.response.Article

class AdapterSave(private var context: Context, private val list: LiveData<List<Article>>) :
    RecyclerView.Adapter<AdapterSave.ViewHolder>() {



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.saveImage_list)!!
        val textview = itemView.findViewById<TextView>(R.id.text_view_list_item)!!
    }

    companion object {

        val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.savelist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 15f
        circularProgressDrawable.centerRadius = 25f
        circularProgressDrawable.start()

        Glide.with(context).load(list.value?.get(position)?.urlToImage)
            .placeholder(circularProgressDrawable).error(R.drawable.error).into(holder.image)
        holder.textview.text = list.value?.get(position)?.title
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }
}