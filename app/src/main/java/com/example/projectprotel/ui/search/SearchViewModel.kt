package com.example.projectprotel.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectprotel.Data.PlantsEntity
import com.example.projectprotel.utils.dataDummy
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    fun getPlants(): List<PlantsEntity> = dataDummy.generateDummyPlants()
}