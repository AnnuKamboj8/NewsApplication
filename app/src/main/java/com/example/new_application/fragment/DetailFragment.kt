@file:Suppress("DEPRECATION")

package com.example.new_application.fragment

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.new_application.R
import com.example.new_application.databinding.FragmentDetailSBinding
import com.example.new_application.retrofit.response.Article
import com.example.new_application.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_s.*


@AndroidEntryPoint
class DetailsFragment : Fragment() {


    private  val viewModel by viewModels<NewsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailSBinding.inflate(inflater, container, false)

        val data = requireArguments()["article"] as Article
        binding.articles = data
        Glide.with(binding.root).load(data.urlToImage).placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.detailImage)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        SaveButton.setOnClickListener {
            if(SaveButton.text == "SAVE NEWS"){
                SaveButton.text = "Article Already Saved"
                SaveButton.setBackgroundColor(Color.BLUE)

                if (article != null)  {
                    viewModel.saveArticle(article)
                }

                Toast.makeText(context, "Article saved successfully", Toast.LENGTH_SHORT).show()
            }else if(SaveButton.text =="Article Already Saved"){
                if (article != null) {
                    viewModel.deleteArticle(article)
                }

               // SaveButton.setText("SAVE NEWS")
                SaveButton.visibility=View.GONE

            }

        }
    }
}


