package com.nipun.marsuploaddemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.model.MediaStoreImage

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class GalleryAdapter(val onClick: (MediaStoreImage) -> Unit) :
    ListAdapter<MediaStoreImage, GalleryAdapter.ImageViewHolder>(MediaStoreImage.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.gallery_layout, parent, false)
        return ImageViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val mediaStoreImage = getItem(position)
        holder.rootView.tag = mediaStoreImage

        Glide.with(holder.imageView)
            .load(mediaStoreImage.contentUri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.imageView)
    }
    /**
     * Basic [RecyclerView.ViewHolder] for our gallery.
     */
   inner class ImageViewHolder(view: View, onClick: (MediaStoreImage) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val rootView = view
        val imageView: ImageView = view.findViewById(R.id.image)

        init {
            imageView.setOnClickListener {
                val image = rootView.tag as? MediaStoreImage ?: return@setOnClickListener
                onClick(image)
            }
        }
    }
}

