package com.example.new_application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.new_application.R
import com.example.new_application.adapter.AdapterClickListener
import com.example.new_application.adapter.NewsPagingAdapter
import com.example.new_application.retrofit.response.Article
import com.example.new_application.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.view.*
import timber.log.Timber


@AndroidEntryPoint
class NewsFragment : Fragment(), AdapterClickListener {


    private val viewModel by viewModels<NewsViewModel>()
    private val newsPagingAdapter = NewsPagingAdapter(this)

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.list.observe(viewLifecycleOwner) {
            newsPagingAdapter.submitData(lifecycle, it)
        }

        newsPagingAdapter.addLoadStateListener { state ->

            when (state.refresh) {
                is LoadState.Loading -> {
                    view.news_progress.visibility = View.VISIBLE

                }
                is LoadState.NotLoading -> {
                    view.news_progress.visibility = View.GONE

                }
                is LoadState.Error -> {
                    view.news_progress.visibility = View.GONE

                    Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
        view.recycler_View.adapter = newsPagingAdapter
    }

    override fun clickListener(article: Article) {
        findNavController().navigate(
            R.id.action_newsFragment_to_detailSFragment,
            bundleOf("article" to article)
        )
    }

}




