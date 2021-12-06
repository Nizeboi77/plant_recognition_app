package com.example.projectprotel.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectprotel.databinding.FragmentSearchBinding
import android.app.SearchManager
import com.example.projectprotel.Data.PlantsEntity
import com.example.projectprotel.ui.info.InfoActivity
import android.util.Log



class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val kategori1 = arrayOf("batas bawah","Berbunga", "Tidak Berbunga", "C","batas atas")
        val kategori2 = arrayOf("batas bawah","Hutan", "Ladang", "Rawa","batas atas")
        val kategori3 = arrayOf("batas bawah","Edible", "Non Edible", "F","batas atas")

        var i = 1
        var j = 1
        var k = 1
        binding.kategori1.text = kategori1[i]
        binding.kategori2.text = kategori2[j]
        binding.kategori3.text = kategori3[k]

        binding.kategori1kiri.setOnClickListener{
            if (i<=1){
                i=3
            }else{
                i-=1
            }
            binding.kategori1.text = kategori1[i]
        }

        binding.kategori1kanan.setOnClickListener{
            if (i>2){
                i=1
            }else{
                i+=1
            }
            binding.kategori1.text = kategori1[i]
        }

        binding.kategori2kiri.setOnClickListener{
            if (j<=1){
                j=3
            }else{
                j-=1
            }
            binding.kategori2.text = kategori2[j]
        }

        binding.kategori2kanan.setOnClickListener{
            if (j>2){
                j=1
            }else{
                j+=1
            }
            binding.kategori2.text = kategori2[j]
        }

        binding.kategori3kiri.setOnClickListener{
            if (k<=1){
                k=3
            }else{
                k-=1
            }
            binding.kategori3.text = kategori3[k]
        }

        binding.kategori3kanan.setOnClickListener{
            if (k>2){
                k=1
            }else{
                k+=1
            }
            binding.kategori3.text = kategori3[k]
        }


        binding.searchbutton.setOnClickListener{

            val searchintent = Intent(context, InfoActivity::class.java)
            searchintent.putExtra("kategori",binding.kategori1.text)
            searchintent.putExtra("kategoridua",binding.kategori2.text)
            searchintent.putExtra("kategoritiga",binding.kategori3.text)
            startActivity(searchintent)
            //var query = binding.kategori1.text

            //var tes = data("A")
            //var escapedQuery = URLEncoder.encode(query as String?, "UTF-8")
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/#q=" + escapedQuery))
           //Log.e("SearchFragment", query as String)
//            if (query ==  "Alfafa" ){
//                val intent = Intent(Intent.ACTION_WEB_SEARCH)
//                intent.putExtra(SearchManager.QUERY, query)
//                startActivity(intent)
//            }else{
//                val nextIntent = Intent(context, InfoActivity::class.java)
//                startActivity(nextIntent)
//            }


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private suspend fun getBitmap(): Bitmap {
//        val loading = ImageLoader(this)
//        val request = ImageRequest.Builder(this)
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }


}