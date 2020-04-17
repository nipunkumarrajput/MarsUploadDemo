package com.nipun.marsuploaddemo.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.adapter.ImageListAdapter
import com.nipun.marsuploaddemo.custom.ImageDialogFragment
import com.nipun.marsuploaddemo.databinding.FragmentImageListBinding
import com.nipun.marsuploaddemo.model.GalleryPicture
import com.nipun.marsuploaddemo.viewmodel.GalleryViewModel

/**
 * A simple [Fragment] subclass.
 */
class ImageListFragment : BaseFragment<GalleryViewModel>(GalleryViewModel::class.java) {
    private lateinit var binding: FragmentImageListBinding
    private val galleryViewModel: GalleryViewModel by viewModels()

    private val pictures by lazy {
        ArrayList<GalleryPicture>(galleryViewModel.getGallerySize(context!!))
    }
    private val adapter by lazy {
        ImageListAdapter(pictures, context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // (requireActivity() as MainActivity).mainContainer.fitsSystemWindows = true
        requestReadStoragePermission()
        binding.fabCamera.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun init() {
        val layoutManager = GridLayoutManager(context, 4)
        val pageSize = 20
        binding.rv.layoutManager = layoutManager
        binding.rv.adapter = adapter

        adapter.setOnClickListener { galleryPicture ->
            ImageDialogFragment.showDialog(childFragmentManager, galleryPicture.path)
        }

        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == pictures.lastIndex) {
                    loadPictures(pageSize)
                }
            }
        })
        loadPictures(pageSize)
    }

    private fun loadPictures(pageSize: Int) {
        galleryViewModel.getImagesFromGallery(context!!, pageSize) {
            if (it.isNotEmpty()) {
                pictures.addAll(it)
                adapter.notifyItemRangeInserted(pictures.size, it.size)
            }
            Log.i("GalleryListSize", "${pictures.size}")
        }
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                context!!,
                readStorage
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else init()
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
                init()
            else if (requestCode == 10) {
                openCamera()
            }
        } else {
            showToast("Permission Required to Fetch Gallery.")
        }
    }

    private fun showToast(s: String) = Toast.makeText(context, s, Toast.LENGTH_SHORT).show()

    private fun openCamera() {
        findNavController().navigate(R.id.cameraFragment)
    }
}
