package com.softdata.dyhxx.violation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softdata.dyhxx.R

class ViolationRvAdapter : RecyclerView.Adapter<ViolationRvAdapter.VH>() {

    private val list = mutableListOf<ViolationCarModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_violation, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size


    fun submitList(list: List<ViolationCarModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearRv() {
        this.list.clear()
        notifyDataSetChanged()
    }


    class VH(val view: View) : RecyclerView.ViewHolder(view) {

        var violation_time: TextView = view.findViewById(R.id.violation_time)
        var violation_act: TextView = view.findViewById(R.id.violation_act)
        var violation_type: TextView = view.findViewById(R.id.violation_type)
        var violation_locatoin: TextView = view.findViewById(R.id.violation_location)
        var violation_sum: TextView = view.findViewById(R.id.violation_sum)

        @SuppressLint("SetTextI18n")
        fun onBind(model: ViolationCarModel) {
            violation_time.text = model.violationTime
            violation_act.text = model.qarorSery + " " + model.qarorNumber
            violation_type.text = model.violationType
            violation_locatoin.text = model.location
            violation_sum.text = model.sum
        }
    }

}

