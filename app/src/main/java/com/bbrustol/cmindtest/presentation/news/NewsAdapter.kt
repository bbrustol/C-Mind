package com.bbrustol.cmindtest.presentation.news

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.data.model.SourcesModel
import kotlinx.android.synthetic.main.item_source_news.view.*

class NewsAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var mSourcesList: ArrayList<SourcesModel> = arrayListOf()
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
        return mSourcesList.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_source_news, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val source = mSourcesList[position]

        holder.itemView.apply {
            source_news_item_tv_title.text = source.name
            source_news_item_tv_description.text = source.description
            source_news_item_website.setOnClickListener {
                clickWebviewButtonSubject.onNext(mSourcesList.get(holder.adapterPosition).url)
            }

            setOnClickListener {
                clickItemSubject.onNext(mSourcesList.get(holder.adapterPosition).id)
            }
        }

        (mRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }


    fun updateData(
        newData: ArrayList<SourcesModel>
    ) {
        mSourcesList = newData
        notifyDataSetChanged()
    }
}

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

