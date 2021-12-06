package com.example.projectprotel.ui.info

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.projectprotel.databinding.FragmentInfoBinding
import com.example.projectprotel.databinding.InfoBinding
import com.example.projectprotel.ui.info.InfoViewModel

class InfoFragment : Fragment()  {

    private lateinit var infoViewModel: InfoViewModel
    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        infoViewModel =
            ViewModelProvider(this).get(InfoViewModel::class.java)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}