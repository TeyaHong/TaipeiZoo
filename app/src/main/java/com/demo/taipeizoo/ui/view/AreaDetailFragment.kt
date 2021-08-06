package com.demo.taipeizoo.ui.view

import android.content.Intent
import android.net.Uri
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
import com.demo.taipeizoo.data.model.Plant
import com.demo.taipeizoo.databinding.FragmentAreaDetailBinding
import com.demo.taipeizoo.ui.adapter.*
import com.demo.taipeizoo.ui.viewmodel.AreaDetailViewModel
import com.demo.taipeizoo.utils.Constants.LIST_NO
import com.demo.taipeizoo.utils.OnLoadMoreScrollListener
import com.demo.taipeizoo.utils.Status


/**
 * 某館區介紹
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaDetailFragment : Fragment() {

    companion object {
        private const val ARG_AREA = "area"

        @JvmStatic
        fun newInstance(area: Area) =
            AreaDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_AREA, area)
                }
            }
    }

    private lateinit var area: Area
    private lateinit var viewModel: AreaDetailViewModel
    private lateinit var adapter: RowAdapter
    private lateinit var onLoadMoreScrollListener: OnLoadMoreScrollListener

    private var offset: Int = 0

    private var _binding: FragmentAreaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            area = it.getSerializable(ARG_AREA) as Area
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreaDetailBinding.inflate(inflater, container, false)
        setupUI()
        setupViewModel()
        setupObserver()
        getPlants()
        return binding.root
    }

    private fun setupUI() {
        requireActivity().title = area.name
        (requireActivity() as MainActivity).binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        (requireActivity() as MainActivity).binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        onLoadMoreScrollListener = OnLoadMoreScrollListener(layoutManager)
        onLoadMoreScrollListener.setOnLoadMoreListener(object :
            OnLoadMoreScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })
        binding.recyclerView.addOnScrollListener(onLoadMoreScrollListener)
        adapter = RowAdapter(object : RowAdapter.OnItemClickListener {
            override fun onItemClick(view: View, t: Any) {
                if (t is Area) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(t.url)))
                } else if (t is Plant) {
                    val areaDetailFragment = PlantDetailFragment.newInstance(t)
                    (requireActivity() as MainActivity).showFragmentWithTransition(
                        this@AreaDetailFragment,
                        areaDetailFragment,
                        PlantDetailFragment::class.java.simpleName
                    )
                }
            }
        })
        binding.recyclerView.adapter = adapter

        adapter.addData(AreaDetailRowData(area = area))
        adapter.addLoadingView()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(AreaDetailViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.result.observe(viewLifecycleOwner, { resource ->
            if (resource.status == Status.LOADING) {
                if (onLoadMoreScrollListener.isLoading()) {
                    adapter.addLoadingView()
                }
            } else {
                if (onLoadMoreScrollListener.isLoading()) {
                    adapter.removeLoadingView()
                }
                if (resource.status == Status.SUCCESS) {
                    resource.data?.let { rowData ->
                        if (onLoadMoreScrollListener.isLoading()) {
                            addLoadMoreData(rowData)
                        } else {
                            adapter.removeLoadingView()
                            retrieveList(rowData)
                        }
                    }
                }
            }
        })
        viewModel.loadMoreAvailable.observe(viewLifecycleOwner, { loadMoreAvailable ->
            onLoadMoreScrollListener.setLoadMoreAvailable(loadMoreAvailable)
        })
    }

    private fun getPlants() {
        viewModel.getPlants(area.name, offset)
    }

    private fun loadMore() {
        offset += LIST_NO
        getPlants()
    }

    private fun addLoadMoreData(loadMoreData: List<RowData>) {
        // adding the data to our main list
        adapter.addData(loadMoreData)
        // change the boolean isLoading to false
        onLoadMoreScrollListener.setLoading()
    }

    private fun retrieveList(rowDataList: List<RowData>) {
        if (rowDataList.isEmpty()) return
        adapter.apply {
            val mutableList = rowDataList.toMutableList()
            mutableList.add(0, PlantSectionRowData())
            addData(mutableList)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            requireActivity().title = area.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}