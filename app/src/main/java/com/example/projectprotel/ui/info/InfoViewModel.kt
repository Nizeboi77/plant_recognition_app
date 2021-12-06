package com.example.projectprotel.ui.info

import android.app.SearchManager
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.projectprotel.Data.PlantsEntity
import com.example.projectprotel.utils.dataDummy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    private lateinit var plantsId: String
    private lateinit var plantsId2: String
    private lateinit var plantsId3: String

    fun setSelectedplant(plantsId: String){
        this.plantsId = plantsId
    }

    fun setSelectedplants(plantsId: String, plantsId2: String, plantsId3: String){
        this.plantsId = plantsId
        this.plantsId2 = plantsId2
        this.plantsId3 = plantsId3
    }

    fun getPlants(): PlantsEntity? {
        var plant: PlantsEntity? =null
        val plantsEntity = dataDummy.generateDummyPlants()
        for (plantsEntity in plantsEntity) {
            if (plantsEntity.kategorisatu == plantsId && plantsEntity.kategoridua == plantsId2 && plantsEntity.kategoritiga == plantsId3) {
                plant = plantsEntity
            }
//            else {
//                val intent = Intent(Intent.ACTION_WEB_SEARCH)
//                intent.putExtra(SearchManager.QUERY, plantsId)
//                startActivity(intent)
//            }
        }
        return plant

    }

    fun getPlant(): PlantsEntity? {
        var plant: PlantsEntity? =null
        val plantsEntity = dataDummy.generateDummyPlants()
        for (plantsEntity in plantsEntity) {
            if (plantsEntity.name == plantsId) {
                plant = plantsEntity
            }
//            else {
//                val intent = Intent(Intent.ACTION_WEB_SEARCH)
//                intent.putExtra(SearchManager.QUERY, plantsId)
//                startActivity(intent)
//            }
        }
        return plant

    }

}