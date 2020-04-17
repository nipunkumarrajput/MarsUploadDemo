package com.nipun.marsuploaddemo.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nipun.marsuploaddemo.MainActivity
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.adapter.GalleryAdapter
import com.nipun.marsuploaddemo.custom.ImageDialogFragment
import com.nipun.marsuploaddemo.databinding.FragmentGalleryBinding
import com.nipun.marsuploaddemo.model.MediaStoreImage
import com.nipun.marsuploaddemo.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : BaseFragment<GalleryViewModel>(GalleryViewModel::class.java) {
    private lateinit var binding: FragmentGalleryBinding

    private val adapter by lazy {
        GalleryAdapter { image ->
            ImageDialogFragment.showDialog(childFragmentManager, image.contentUri.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gallery.also { rView ->
            rView.layoutManager = GridLayoutManager(requireContext(), 3)
            rView.adapter = adapter
        }

        viewModel.images.observe(viewLifecycleOwner, Observer<List<MediaStoreImage>> { images ->
            adapter.submitList(images)
            binding.progressBar.visibility = View.GONE
        })

        binding.fabCamera.setOnClickListener {
            requestCameraPermission()
        }

        requestReadStoragePermission()
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                context!!,
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else viewModel.loadImages()
    }


    private fun showToast(s: String) = Toast.makeText(context, s, Toast.LENGTH_SHORT).show()

    private fun openCamera() {
        findNavController().navigate(R.id.cameraFragment)
    }

    private fun requestCameraPermission() {
        val camera = Manifest.permission.CAMERA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                context!!,
                camera
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(camera), 10)
        } else openCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            if (requestCode == 3)
                viewModel.loadImages()
            else if (requestCode == 10) {
                openCamera()
            }
        } else {
            showToast("Permission Required to Fetch Gallery.")
        }
    }
}
