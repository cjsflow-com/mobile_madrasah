package com.example.man2superapp.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.man2superapp.source.local.model.LocalCounselingSession

class BaseDiffCallback<T> (
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsSame: (T, T) -> Boolean,
    private val areContentSame: (T,T) -> Boolean
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areContentSame(oldList[oldItemPosition], newList[newItemPosition])
}