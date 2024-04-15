package com.example.new_application.adapter




import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.new_application.BR
import com.example.new_application.R
import com.example.new_application.databinding.ListItemBinding
import com.example.new_application.retrofit.response.Article
import kotlinx.android.synthetic.main.list_item.view.*


class NewsPagingAdapter(private val adapterClickListener: AdapterClickListener) :
    PagingDataAdapter<Article, NewsPagingAdapter.MyViewHolder>(DIFF_UTIL) {
 private var context :Context?=null

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

    inner class MyViewHolder(val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsPagingAdapter.MyViewHolder {
        context=parent.context
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
        circularProgressDrawable?.strokeWidth = 15f
        circularProgressDrawable?.centerRadius = 25f
        circularProgressDrawable?.start()

        val item = getItem(position)
        holder.viewDataBinding.setVariable(BR.article, item)
        Glide.with(holder.viewDataBinding.root).load(item!!.urlToImage).placeholder(circularProgressDrawable).error(R.drawable.error)
            .into(holder.viewDataBinding.root.image_list)

        holder.viewDataBinding.root.list_item_root.setOnClickListener {

            adapterClickListener.clickListener(item)
        }
    }

    val differ = AsyncListDiffer(this, DIFF_UTIL)
}
