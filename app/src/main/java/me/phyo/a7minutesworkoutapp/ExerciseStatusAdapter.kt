package me.phyo.a7minutesworkoutapp

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.phyo.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import me.phyo.a7minutesworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsCompleted()->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_red)
                holder.tvItem.setTextColor(Color.parseColor("#EDF2F4"))
            }
            model.getIsSelected()->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_selected)
                holder.tvItem.setTextColor(Color.parseColor("#EF233C"))
            }
            else->{
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_gray)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}