package duggu.automaticscroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_adapter.view.*

class NewsAdapter(var dataArray : Array<String?>,var listener : View.OnClickListener) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false))

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        fun bind() {
            dataArray[layoutPosition%dataArray.size]?.let { model->
                itemView.tvNews.setOnClickListener(this)
                itemView.tvNews.text = model
            }
        }

        override fun onClick(v: View?) {
            v?.setTag(R.id.model ,dataArray[layoutPosition%dataArray.size])
            listener.onClick(v)
        }
    }
}