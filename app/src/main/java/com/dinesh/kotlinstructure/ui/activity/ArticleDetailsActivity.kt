package com.dinesh.kotlinstructure.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dinesh.kotlinstructure.R
import com.dinesh.kotlinstructure.common.DataMapper
import com.dinesh.kotlinstructure.common.toEditable
import com.dinesh.kotlinstructure.databinding.ActivityArticleDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.label_details)
        val binding = DataBindingUtil.setContentView<ActivityArticleDetailsBinding>(this, R.layout.activity_article_details)
        binding.model = DataMapper.mArticleDetails[intent.getIntExtra("position", 0)]
        binding.model?.run {
            media?.takeIf { it.isNotEmpty() }?.run {
                get(0).takeIf { it.mediaMetadata != null && it.mediaMetadata.isNotEmpty() && it.type.equals("image", ignoreCase = true) }?.run {
                    Picasso.get().load(mediaMetadata!![0]?.url).into(ivImage)
                } ?: run { ivImage.visibility = View.GONE }
            }
        }
    }

    override fun onBackPressed() {
        tilTitle.hint = "".toEditable()
        tilDesc.hint = "".toEditable()
        super.onBackPressed()
    }


}
