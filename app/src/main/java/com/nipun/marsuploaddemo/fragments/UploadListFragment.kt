package com.nipun.marsuploaddemo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nipun.marsuploaddemo.MainActivity
import com.nipun.marsuploaddemo.adapter.UploadListAdapter
import com.nipun.marsuploaddemo.custom.ImageDialogFragment
import com.nipun.marsuploaddemo.data.db.UploadEntity
import com.nipun.marsuploaddemo.databinding.FragmentUploadListBinding
import com.nipun.marsuploaddemo.viewmodel.UploadListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class UploadListFragment : BaseFragment<UploadListViewModel>(UploadListViewModel::class.java) {
    private lateinit var binding: FragmentUploadListBinding

    private val adapter by lazy {
        UploadListAdapter { image ->
            ImageDialogFragment.showDialog(childFragmentManager, null, image.imageUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gallery.also { rView ->
            rView.layoutManager = GridLayoutManager(requireContext(), 4)
            rView.adapter = adapter
        }

        viewModel.images.observe(viewLifecycleOwner, Observer<List<UploadEntity>> { images ->
            adapter.submitList(images)
            binding.progressBar.visibility = View.GONE
        })

        viewModel.loadImages()
    }
}
