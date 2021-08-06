package com.demo.taipeizoo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.taipeizoo.data.repository.ApiRepository
import com.demo.taipeizoo.ui.adapter.PlantRowData
import com.demo.taipeizoo.ui.adapter.RowData
import com.demo.taipeizoo.utils.Constants
import com.demo.taipeizoo.utils.Resource
import kotlinx.coroutines.launch

/**
 * 某館區介紹 ViewModel
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaDetailViewModel : ViewModel() {
    private val apiRepository: ApiRepository by lazy { ApiRepository() }

    private val _result = MutableLiveData<Resource<List<RowData>>>()
    val result: LiveData<Resource<List<RowData>>> = _result

    private val _loadMoreAvailable = MutableLiveData<Boolean>()
    val loadMoreAvailable: LiveData<Boolean> = _loadMoreAvailable

    private var plantList: MutableList<String> = mutableListOf()

    fun getPlants(q: String, offset: Int) {
        viewModelScope.launch {
            _result.value = Resource.loading()
            try {
                val list = apiRepository.getPlants(q = q, offset = offset).result.results
                val newList = list.distinctBy { it.nameCh }
                val newData = mutableListOf<RowData>()
                for (i in newList.indices) {
                    // 移除API重複的資料
                    if (!plantList.contains(newList[i].nameCh)) {
                        plantList.add(newList[i].nameCh)
                        val item = PlantRowData(newList[i])
                        newData.add(item)
                    }
                }
                _result.value = Resource.success(data = newData)
                if (list.size < Constants.LIST_NO) {
                    // no more data
                    _loadMoreAvailable.value = false
                }
            } catch (e: Exception) {
                _result.value = Resource.error(error = e.message)
            }
        }
    }
}