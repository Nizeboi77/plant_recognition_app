package com.example.projectprotel.ui.scan

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectprotel.databinding.FragmentScanBinding
import com.example.projectprotel.ui.info.InfoActivity
import java.io.File
import kotlin.math.max
import kotlin.math.min

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private const val GALLERY_REQUEST_CODE = 2
private lateinit var photoFile : File
private lateinit var currentPhotoPath : String


class ScanFragment : Fragment() {
    //ImageView mImageview
    private lateinit var scanViewModel: ScanViewModel
    private var _binding: FragmentScanBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scanViewModel =
            ViewModelProvider(this).get(ScanViewModel::class.java)

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.gallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }


        binding.camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = context?.let { it1 ->
                FileProvider.getUriForFile(
                    it1, "com.example.projectprotel.fileprovider",
                    photoFile)
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            if (context?.let { it1 -> takePictureIntent.resolveActivity(it1.packageManager) } != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(context, "unable to open camera", Toast.LENGTH_SHORT).show()
            }

        }

        return root
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    private fun getCapturedImage(): Bitmap {
        // Get the dimensions of the View
        val targetW: Int = binding.imagecam.width
        val targetH: Int = binding.imagecam.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = max(1, min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inMutable = true
        }

        val exifInterface = ExifInterface(currentPhotoPath)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(bitmap, 180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(bitmap, 270f)
            }
            else -> {
                bitmap
            }
        }
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //val takenImage = data?.extras?.get("data") as Bitmap
        if(resultCode == Activity.RESULT_OK){

            when (requestCode){

                REQUEST_CODE -> {
                    val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
                    binding.imagecam.setImageBitmap(takenImage)

                    binding.nextbtn.setOnClickListener {
                        val nextIntent = Intent(context, InfoActivity::class.java)
                        nextIntent.putExtra("picture", photoFile.absolutePath)
                        startActivity(nextIntent)
                    }

                }

                GALLERY_REQUEST_CODE -> {


                    val galleryimage = data?.extras?.get("data") as Bitmap
                    binding.imagecam.setImageBitmap(galleryimage)
                }
            }


        }

    }

}