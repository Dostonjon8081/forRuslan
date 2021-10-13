package uz.fizmasoft.dyhxx.violation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.fizmasoft.dyhxx.R

class ViolationRvAdapter : RecyclerView.Adapter<ViolationRvAdapter.VH>() {

    private val list = mutableListOf<ViolationCarModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_violation_twoo, parent, false)
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

        private var violation_time: TextView = view.findViewById(R.id.violation_time)
        private var violation_act: TextView = view.findViewById(R.id.violation_act)
        private var violation_type: TextView = view.findViewById(R.id.violation_type)
        private var violation_locatoin: TextView = view.findViewById(R.id.violation_location)
        private var violation_sum: TextView = view.findViewById(R.id.violation_sum)
        private var violation_img: ImageView = view.findViewById(R.id.violation_img)

        @SuppressLint("SetTextI18n")
        fun onBind(model: ViolationCarModel) {
            violation_time.text = model.violationTime
            violation_act.text = model.qarorSery + " " + model.qarorNumber
            violation_type.text = model.violationType
            violation_locatoin.text = model.location
            violation_sum.text = model.sum+" "+view.context.resources.getString(R.string.sum)

            violation_img.setImageResource(when(model.qarorSery){
                "KV"->R.drawable.ic_policeman
                "RR"->R.drawable.ic_artist
                else->R.drawable.ic_radar
            })
        }
    }

}

