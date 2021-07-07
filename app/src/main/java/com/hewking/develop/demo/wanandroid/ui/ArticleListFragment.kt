package com.hewking.develop.demo.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hewking.develop.databinding.FragmentListBinding
import com.hewking.develop.demo.wanandroid.ServiceLocator
import com.hewking.develop.demo.wanandroid.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleListFragment: Fragment() {

    lateinit var binding: FragmentListBinding

    private lateinit var adapter: ArticleAdapter

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

        initSwipeToRefresh()

        adapter = ArticleAdapter()

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenCreated {
            model.flow.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.refreshLayout.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.list.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.list.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.list.adapter = adapter.withLoadStateFooter(FooterAdapter { adapter.retry() })

    }

    private fun initSwipeToRefresh() {
        binding.refreshLayout.setOnRefreshListener { adapter.refresh() }
    }

}