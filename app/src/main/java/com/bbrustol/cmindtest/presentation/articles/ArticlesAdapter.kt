package com.bbrustol.cmindtest.presentation.articles

import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.data.model.EverythingModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_everything_articles.view.*

class ArticlesAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var mArticlesList: ArrayList<EverythingModel> = arrayListOf()
    private lateinit var mRecyclerView: RecyclerView

    private val clickWebviewButtonSubject = PublishSubject.create<String>()
    val clickWebviewButtonEvent: Observable<String> = clickWebviewButtonSubject

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return mArticlesList.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_everything_articles, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val articles = mArticlesList[position]

        holder.itemView.apply {
            item_everything_articles_tv_title.text = articles.title
            item_everything_articles_tv_description.text = articles.description
            item_everything_articles_tv_author.text = articles.author
            item_everything_articles_tv_date.text = articles.publishedAt

            setOnClickListener {
                clickWebviewButtonSubject.onNext(articles.url)
            }

            //load image
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            if (articles.urlToImage.isNullOrBlank()) {
                item_everything_articles_thumbnail.visibility = View.GONE
            }else {
                item_everything_articles_thumbnail.visibility = View.VISIBLE
                Glide.with(context)
                    .load(articles.urlToImage)
                    .apply(RequestOptions()
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(item_everything_articles_thumbnail)
            }
        }
    }

    fun updateData(
        newData: ArrayList<EverythingModel>
    ) {
        mArticlesList = newData
        notifyDataSetChanged()
    }
}

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)