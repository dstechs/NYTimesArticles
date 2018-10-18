package com.dinesh.kotlinstructure.ui.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dinesh.kotlinstructure.BR
import com.dinesh.kotlinstructure.R
import com.dinesh.kotlinstructure.common.DataMapper
import com.dinesh.kotlinstructure.models.ResultModel
import com.dinesh.kotlinstructure.ui.activity.ArticleDetailsActivity
import com.dinesh.kotlinstructure.util.AnimUtils

class ArticlesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var animPosition = -1
    private val VIEW_PROGRESS = 0
    private val VIEW_ITEM = 1
    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_ITEM -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(container.context), R.layout.item_short_desc, container, false)
                return ArticleHolder(binding)
            }
            else -> return ProgressHolder(LayoutInflater.from(container.context).inflate(R.layout.item_progress, container, false))
        }
    }

    override fun getItemCount(): Int {
        return DataMapper.mArticleDetails.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleHolder) {
            if (animPosition < position) {
                AnimUtils.translate(holder.itemView)
                animPosition = position
            }
            holder.binding.setVariable(BR.model, DataMapper.mArticleDetails[position])
            holder.binding.executePendingBindings()
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ArticleDetailsActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity,
                        Pair(holder.itemView.findViewById(R.id.tvType), "type"),
                        Pair(holder.itemView.findViewById(R.id.tvTitle), "title"))
                intent.putExtra("position", position)
                holder.itemView.context.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (DataMapper.mArticleDetails[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }

    fun hideLoadProgress() {
        DataMapper.mArticleDetails.removeAt(DataMapper.mArticleDetails.size - 1)
        notifyItemRemoved(DataMapper.mArticleDetails.size)
    }

    fun showLoadProgress() {
        DataMapper.mArticleDetails.add(null)
        notifyItemInserted(DataMapper.mArticleDetails.size - 1)
    }

    fun updateData(detailModels: List<ResultModel>) {
        DataMapper.mArticleDetails.addAll(detailModels)
        notifyItemInserted(DataMapper.mArticleDetails.size)
    }

    class ArticleHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    class ProgressHolder(view: View) : RecyclerView.ViewHolder(view)
}