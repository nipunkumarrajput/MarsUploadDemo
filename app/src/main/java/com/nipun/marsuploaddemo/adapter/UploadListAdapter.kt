package com.nipun.marsuploaddemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.data.db.UploadEntity

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class UploadListAdapter(val onClick: (UploadEntity) -> Unit) :
    ListAdapter<UploadEntity, UploadListAdapter.ImageViewHolder>(UploadEntity.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.gallery_layout, parent, false)
        return ImageViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uploadEntity = getItem(position)
        holder.rootView.tag = uploadEntity

        Glide.with(holder.imageView)
            .load(uploadEntity.imageThumbUrl)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.imageView)
    }

    inner class ImageViewHolder(view: View, onClick: (UploadEntity) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val rootView = view
        val imageView: ImageView = view.findViewById(R.id.image)

        init {
            imageView.setOnClickListener {
                val image = rootView.tag as? UploadEntity ?: return@setOnClickListener
                onClick(image)
            }
        }
    }

}
