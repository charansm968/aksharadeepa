package com.akshara.deepa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.akshara.deepa.data.models.ActivityItem
import com.akshara.deepa.databinding.ItemRecentActivityBinding

class RecentActivityAdapter(private val items: List<ActivityItem>) :
    RecyclerView.Adapter<RecentActivityAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemRecentActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(items[pos])

    inner class VH(private val b: ItemRecentActivityBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: ActivityItem) {
            b.tvActivityEmoji.text    = item.emoji
            b.tvActivityTitle.text    = item.title
            b.tvActivitySubtitle.text = item.subtitle
            b.tvActivityTime.text     = item.timeAgo
            b.tvActivityEmoji.setTextColor(
                ContextCompat.getColor(b.root.context, item.colorRes))
            b.root.alpha = 0f; b.root.translationX = -30f
            b.root.animate().alpha(1f).translationX(0f)
                .setDuration(280).setStartDelay(adapterPosition * 55L)
                .setInterpolator(DecelerateInterpolator()).start()
        }
    }
}
