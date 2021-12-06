package com.example.projectprotel.ui.info

import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projectprotel.Data.PlantsEntity
import com.example.projectprotel.R
import com.example.projectprotel.databinding.FragmentInfoBinding
import com.example.projectprotel.databinding.InfoBinding
import com.example.projectprotel.ml.Model
import com.example.projectprotel.utils.dataDummy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector


class InfoActivity : AppCompatActivity(){
    private lateinit var binding: InfoBinding
    private lateinit var imageInBitMapAfterResize: Bitmap
    //private lateinit var imageInBitMapAfterResizeGal: Bitmap

    companion object {
        const val kategori = "kategori"
        const val kategoridua = "kategoridua"
        const val kategoritiga = "kategoritiga"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = InfoBinding.inflate(layoutInflater)
        //setContentView(bindings.root)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[InfoViewModel::class.java]

        BottomSheetBehavior.from(binding.infoframe).apply {
            peekHeight=200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val Picture = getIntent().getStringExtra("picture")
        val PictureGal = getIntent().getStringExtra("picturegal")
        val Apa = BitmapFactory.decodeFile(Picture)
        val Gapa = BitmapFactory.decodeFile(PictureGal)

        binding.gambarinfo.setImageBitmap(Apa)
        //binding.gambarinfo.setImageBitmap(Gapa)
        if (Apa == null){

            val extras = intent.extras
            val plantId = extras?.getString(kategori)
            val plantId2 = extras?.getString(kategoridua)
            val plantId3 = extras?.getString(kategoritiga)

            if (plantId != null) {
                if (plantId2 != null) {
                    if (plantId3 != null) {
                        viewModel.setSelectedplants(plantId, plantId2, plantId3)
                    }
                }
                viewModel.getPlants()?.let { populateplant(it) }
            }

//            else{
//                val webintent = Intent(Intent.ACTION_WEB_SEARCH)
//                webintent.putExtra(SearchManager.QUERY, plantId)
//                startActivity(webintent)
//            }
        }
        else{
            //imageInBitMapAfterResize=resizePhoto(Apa)
            //imageInBitMapAfterResizeGal=resizePhoto(Gapa)
            scans(Apa)
        }


    }

    private fun scans(bitmap: Bitmap){
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[InfoViewModel::class.java]
        val model = Model.newInstance(this)

// Creates inputs for reference.
        val image = TensorImage.fromBitmap(bitmap)

// Runs model inference and gets result.
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList
        //probability.sort(comparator : Comparator<in probability>)
        //probability.maxByOrNull { it.score }?.label ?: "NO_PLANTS"
        val hasil = probability.maxByOrNull { it.score }?.label ?: "NO_PLANTS"
        //binding.judul.text = hasil
        viewModel.setSelectedplant(hasil)
        viewModel.getPlant()?.let { populateplant(it) }

// Releases model resources if no longer used.
        model.close()


    }

    private fun scan(bitmap: Bitmap) {
        val image = TensorImage.fromBitmap(bitmap)

        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.3f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            this,
            "model.tflite",
            options
        )
        val results = detector.detect(image)

        val resultToDisplay = results.map {
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"
            //DetectionResult(it.boundingBox, text)
        }
        val textWithResult = resultToDisplay.toString()
        //val imgWithResult = image
        runOnUiThread {
            binding.judul.setText(textWithResult)
            //binding.gambarinfo.setImageBitmap(imgWithResult)
        }
    }

    private fun populateplant(plantsEntity: PlantsEntity) {
        with(binding){
            judul.text = plantsEntity.name
            deskripsi.text = plantsEntity.characteristics
        }
        Glide.with(this)
            .load(plantsEntity.gambar)
            .apply(RequestOptions().override(3500, 2000))
            .centerCrop()
            .into(binding.gambarinfo)
    }

    fun resizePhoto(bitmap: Bitmap): Bitmap {


        val w = bitmap.width
        val h = bitmap.height
        val aspRat = w / h
        val W = 224
        val H = W * aspRat
        val b = Bitmap.createScaledBitmap(bitmap, W, H, false)

        return b


    }

}