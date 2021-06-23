package com.hewking.develop.demo.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hewking.develop.databinding.FragmentListBinding
import com.hewking.develop.demo.wanandroid.ServiceLocator
import com.hewking.develop.demo.wanandroid.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest

class ArticleListFragment: Fragment() {

    lateinit var binding: FragmentListBinding

    private val model: ArticleViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = ServiceLocator.instance()
                    .getRepository()
                @Suppress("UNCHECKED_CAST")
                return ArticleViewModel(repo) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArticleAdapter()

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            model.flow.collectLatest {
                adapter.submitData(it)
            }
        }


    }

}