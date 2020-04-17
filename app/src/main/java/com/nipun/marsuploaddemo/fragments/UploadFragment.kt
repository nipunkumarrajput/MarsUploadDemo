package com.nipun.marsuploaddemo.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.utils.Constants
import com.nipun.marsuploaddemo.viewmodel.UploadViewModel
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.net.URI

/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class UploadFragment : BaseFragment<UploadViewModel>(UploadViewModel::class.java) {
    private val args: UploadFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frgament_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Crop the image
        val uri = Uri.parse(args.imageUri)
        CropImage.activity(uri).start(requireContext(), this)
    }

    //get image mimetype
    private fun File.getMimeType(fallback: String = "image/*"): String {
        return MimeTypeMap.getFileExtensionFromUrl(toString())
            ?.run { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase()) }
            ?: fallback // You might set it to */*
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val resultUri = result.uri

                    val f = File(URI(resultUri.toString()))
                    Log.d("onActivityResult", f.absolutePath)
                    //Upload cropped image
                    uploadImage(f)
                }
                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    val error = result.error
                    findNavController().popBackStack()
                }
                else -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    //Upload image file
    private fun uploadImage(file: File) {
        viewModel.uploadImage(
            Constants.API_KEY,
            file,
            "${System.currentTimeMillis()}",
            file.getMimeType()
        ) {
            if (it != null) {
                Toast.makeText(requireContext(), "Image Uploaded successfully", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "An Error occurred while uploading",
                    Toast.LENGTH_LONG
                ).show()
            }
            findNavController().navigate(UploadFragmentDirections.actionUploadFragmentToGalleryFragment())
        }
    }
}