package net.vrgsoft.rollinglayoutmanagerkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList


class SimpleAdapter @JvmOverloads constructor(private var mData: List<String>? = ArrayList()) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {

    fun setData(data: List<String>) {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return SimpleViewHolder(inflater.inflate(R.layout.item_simple, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(mData!![position])
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView

        init {
            mTextView = itemView.findViewById(R.id.title)
        }

        fun bind(data: String) {
            mTextView.text = data
        }
    }
}
