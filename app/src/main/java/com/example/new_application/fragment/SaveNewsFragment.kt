@file:Suppress("DEPRECATION")

package com.example.new_application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_application.R
import com.example.new_application.adapter.AdapterClickListener
import com.example.new_application.adapter.AdapterSave
import com.example.new_application.adapter.NewsPagingAdapter
import com.example.new_application.retrofit.response.Article
import com.example.new_application.viewModel.NewsViewModel
import com.example.new_application.viewModel.SaveNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_save_news.*


@AndroidEntryPoint
class SaveNewsFragment : Fragment(), AdapterClickListener {

    private val viewModel by viewModels<NewsViewModel>()
    private val savedNewsViewModel by viewModels<SaveNewsViewModel>()
    private val newsPagingAdapter = NewsPagingAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsPagingAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Toast.makeText(requireContext(),"Article Deleted",Toast.LENGTH_SHORT).show()

                    if(viewHolder.adapterPosition==0){
                         no_article_image.visibility=VISIBLE
                    }
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recycler_ViewSavedNews)

        }
        savedNewsViewModel.list.observe(viewLifecycleOwner) {
            newsPagingAdapter.differ.submitList(it)
            recycler_ViewSavedNews.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            val adapter = activity?.let { AdapterSave(it, savedNewsViewModel.list) }


            recycler_ViewSavedNews.adapter = adapter
            if (it.isNullOrEmpty()) {
                no_article_image.visibility = VISIBLE
                Toast.makeText(requireContext(),"No Article to Show",Toast.LENGTH_SHORT).show()
            } else
                no_article_image.visibility = GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // viewModel.getSavedNews()
        return inflater.inflate(R.layout.fragment_save_news, container, false)
    }

    override fun clickListener(article: Article) {

    }
}

