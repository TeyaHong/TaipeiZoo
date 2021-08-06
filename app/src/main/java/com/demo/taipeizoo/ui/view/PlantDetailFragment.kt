package com.demo.taipeizoo.ui.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.demo.taipeizoo.R
import com.demo.taipeizoo.data.model.Plant
import com.demo.taipeizoo.databinding.FragmentPlantDetailBinding

/**
 * 植物介紹
 *
 * Created by TeyaHong on 2021/8/4
 */
class PlantDetailFragment : Fragment() {

    companion object {
        private const val ARG_PLANT = "plant"

        @JvmStatic
        fun newInstance(plant: Plant) =
            PlantDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PLANT, plant)
                }
            }
    }

    private lateinit var plant: Plant

    private var _binding: FragmentPlantDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            plant = it.getSerializable(ARG_PLANT) as Plant
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        requireActivity().title = plant.nameCh
        Glide
            .with(this)
            .load(plant.pic)
            .centerCrop()
            .into(binding.ivPic)
        binding.tvNameCh.text = plant.nameCh
        binding.tvNameEn.text = plant.nameEn
        if (plant.alsoKnown.isNotEmpty()) {
            binding.llPlantDetail.addView(
                createContentView(
                    getString(R.string.also_known),
                    plant.alsoKnown
                )
            )
        }
        if (plant.brief.isNotEmpty()) {
            binding.llPlantDetail.addView(createContentView(getString(R.string.brief), plant.brief))
        }
        if (plant.feature.isNotEmpty()) {
            binding.llPlantDetail.addView(
                createContentView(
                    getString(R.string.feature),
                    plant.feature
                )
            )
        }
        if (plant.function.isNotEmpty()) {
            binding.llPlantDetail.addView(
                createContentView(
                    getString(R.string.function),
                    plant.function
                )
            )
        }
        binding.llPlantDetail.addView(createContentView(getString(R.string.update), plant.update))
    }

    private fun createContentView(field1: String, field2: String): View {
        val view = layoutInflater.inflate(R.layout.item_content, binding.llPlantDetail, false)
        val content = view.findViewById<TextView>(R.id.tv_content)
        if (field1 == getString(R.string.update)) {
            content.text = String.format("%s：%s", field1, field2)
            content.gravity = Gravity.END
        } else {
            content.text = String.format("%s\n%s", field1, field2)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}