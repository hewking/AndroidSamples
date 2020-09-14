package com.hewking.develop.demo

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hewking.develop.R
import kotlinx.android.synthetic.main.activity_browse_album.*
import kotlin.concurrent.thread

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/9/14 16:54
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class BrowseAlbumActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BrowseAlbumActivity"
    }

    val imageList = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_album)
        recyclerView.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                val columns = 3
                val imageSize = recyclerView.width / columns
                val adapter = AlbumAdapter(this@BrowseAlbumActivity, imageList, imageSize)
                recyclerView.layoutManager = GridLayoutManager(this@BrowseAlbumActivity, columns)
                recyclerView.adapter = adapter
                loadImages(adapter)
                return false
            }
        })
    }

    private fun loadImages(adapter: AlbumAdapter) {
        thread {
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                "${MediaStore.MediaColumns.DATE_ADDED} desc"
            )
            if (cursor != null) {
                Log.d(TAG, "loadImages")
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d(TAG, "uri: ${uri}")
                    imageList.add(uri)
                }
                cursor.close()
            }
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    class AlbumAdapter(val context: Context, val imageList: List<Uri>, val imageSize: Int) :
        RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.album_image_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = imageList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imageView.layoutParams.width = imageSize
            holder.imageView.layoutParams.height = imageSize
            val uri = imageList[position]
            val options = RequestOptions().placeholder(R.drawable.album_loading_bg)
                .override(imageSize, imageSize)
            Glide.with(context).load(uri).apply(options).into(holder.imageView)
        }

    }

}