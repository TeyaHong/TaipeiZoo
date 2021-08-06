package com.demo.taipeizoo.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.taipeizoo.MainActivity
import com.demo.taipeizoo.R
import com.demo.taipeizoo.data.model.Area
import com.demo.taipeizoo.databinding.FragmentAreaListBinding
import com.demo.taipeizoo.ui.adapter.RowAdapter
import com.demo.taipeizoo.ui.viewmodel.AreaListViewModel
import com.demo.taipeizoo.utils.Status


/**
 * 館區列表
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaListFragment : Fragment() {

    companion object {
        fun newInstance() = AreaListFragment()
    }

    private lateinit var viewModel: AreaListViewModel
    private lateinit var adapter: RowAdapter

    private var _binding: FragmentAreaListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreaListBinding.inflate(inflater, container, false)
        setupUI()
        setupViewModel()
        setupObserver()
        refresh()
        return binding.root
    }

    private fun setupUI() {
        // Set SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        // Set RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        adapter = RowAdapter(object : RowAdapter.OnItemClickListener {
            override fun onItemClick(view: View, t: Any) {
                val areaDetailFragment = AreaDetailFragment.newInstance(t as Area)
                (requireActivity() as MainActivity).showFragmentWithTransition(
                    this@AreaListFragment,
                    areaDetailFragment,
                    AreaDetailFragment::class.java.simpleName
                )
            }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(AreaListViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.liveData.observe(viewLifecycleOwner, { resource ->
            if (resource.status == Status.LOADING) {
                if (!binding.swipeRefreshLayout.isRefreshing) {
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
                if (resource.status == Status.SUCCESS) {
                    resource.data?.let { list ->
                        adapter.apply {
                            update(list)
                        }
                    }
                }
            }
        })
    }

    private fun refresh() {
        viewModel.getAreas()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            requireActivity().title = getString(R.string.main_title)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}