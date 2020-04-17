package com.nipun.marsuploaddemo.custom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.databinding.FragmentImageDialogBinding


/*
* Created by Nipun Kumar Rajput on 13-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class ImageDialogFragment : DialogFragment() {
    companion object {
        fun showDialog(
            childFragmentManager: FragmentManager,
            imagePath: String?,
            imageUrl: String? = null
        ) {
            val imageDialogFragment = ImageDialogFragment()
            imageDialogFragment.arguments = bundleOf().apply {
                putString("imagePath", imagePath)
                putString("imageUrl", imageUrl)
            }
            imageDialogFragment.show(childFragmentManager, "imageDialogFragment")
        }

    }

    override fun onStart() {
        super.onStart()

        // safety check
        if (dialog == null) {
            return
        }
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.95).toInt(),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // set the animations to use on showing and hiding the dialog
        dialog?.window?.setWindowAnimations(R.style.dialog_animation_fade)
        dialog?.window?.setBackgroundDrawableResource(android.R.drawable.screen_background_dark_transparent)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val imagePath = arguments?.getString("imagePath")
        val imageUrl = arguments?.getString("imageUrl")
        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val binding = FragmentImageDialogBinding.inflate(LayoutInflater.from(activity))
        dialog.setContentView(binding.root)
        // Load the high-resolution "zoomed-in" image.
        if (imagePath.isNullOrEmpty())
            Glide.with(this).load(imageUrl).into(binding.ivExpand)
        else
            Glide.with(this).load(imagePath).into(binding.ivExpand)
        //if coming from upload list then hide upload button
        if (!imageUrl.isNullOrEmpty()) {
            binding.ivUpload.hide()
        }
        binding.ivCancel.setOnClickListener {
            dialog.dismiss()
        }
        binding.ivUpload.setOnClickListener {
            findNavController().navigate(R.id.uploadFragment, bundleOf().apply {
                putString("imageUri", imagePath)
            })
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        return dialog
    }

}