package com.bbrustol.cmindtest.presentation.articles

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.data.model.EverythingModel
import kotlinx.android.synthetic.main.item_everything_articles.view.*

class ArticlesAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var mArticlesList: ArrayList<EverythingModel> = arrayListOf()
    private lateinit var mRecyclerView: RecyclerView

    private val clickWebviewButtonSubject = PublishSubject.create<String>()
    val clickWebviewButtonEvent: Observable<String> = clickWebviewButtonSubject

    private val clickItemSubject = PublishSubject.create<String>()
    val clickItemEvent: Observable<String> = clickItemSubject

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
            everything_articles_item_tv_title.text = articles.author
            everything_articles_item_tv_description.text = articles.description
        }

        (mRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }


    fun updateData(
        newData: ArrayList<EverythingModel>
    ) {
        mArticlesList = newData
        notifyDataSetChanged()
    }
}

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)