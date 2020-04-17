package com.nipun.marsuploaddemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.nipun.marsuploaddemo.R
import com.nipun.marsuploaddemo.model.GalleryPicture
import com.nipun.marsuploaddemo.utils.Utility
import kotlinx.android.synthetic.main.gallery_listitem.view.*

/*
* Created by Nipun Kumar Rajput on 13-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class ImageListAdapter(private val list: List<GalleryPicture>) : RecyclerView.Adapter<GVH>() {
    private lateinit var options: RequestOptions
    private var size = 0f
    private var padding = 0

    constructor(list: List<GalleryPicture>, context: Context) : this(list) {
        size = Utility.convertDpToPixel(72f, context) - 2
        // padding = (size / 3.5) as Int
        options = RequestOptions().override(256).transform(CenterCrop())
            .transform(FitCenter())
    }

    private lateinit var onClick: (GalleryPicture) -> Unit

    fun setOnClickListener(onClick: (GalleryPicture) -> Unit) {
        this.onClick = onClick
    }

    private fun getItem(position: Int) = list[position]

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GVH {
        val vh =
            GVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.gallery_listitem, parent, false)
            )
        vh.containerView.setOnClickListener {
            val position = vh.adapterPosition
            val picture = getItem(position)
            picture.imageView = vh.itemView.ivImg
            onClick(picture)

        }
        return vh
    }

    override fun onBindViewHolder(holder: GVH, position: Int) {
        val picture = list[position]
        Glide.with(holder.containerView).load(picture.path).apply(options)
            .into(holder.itemView.ivImg)
    }

}