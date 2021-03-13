package uz.abbos_dilmurodjonov.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 11.03.2021.
 */
class RecyclerAdapter<T>(private val layoutId: Int, val listener: AdapterListener<T>) :
    RecyclerView.Adapter<RecyclerAdapter<T>.ViewHolder?>() {

    var list: MutableList<T>? = null
        set(value) {
            field?.let { notifyItemRangeRemoved(0, it.size) }

            value?.let {
                field = it
                notifyItemRangeInserted(0, it.size)
            }
        }

    fun addItems(list: List<T>) {
        this.list?.let {
            val position = it.size
            it.addAll(list)
            notifyItemRangeInserted(position, list.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil
            .inflate<ViewDataBinding>(inflater, layoutId, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class ViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(item: T) {
            listener.bindItem(dataBinding, item)
        }

        init {
            listener.setController(dataBinding)
        }
    }

    interface AdapterListener<T> {
        fun setController(dataBinding: ViewDataBinding?)
        fun bindItem(dataBinding: ViewDataBinding?, item: T)
    }


}