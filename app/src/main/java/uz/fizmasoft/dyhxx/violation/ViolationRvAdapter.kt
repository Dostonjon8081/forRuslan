package uz.fizmasoft.dyhxx.violation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import uz.fizmasoft.dyhxx.R

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

        private var violationTimeTitle: TextView = view.findViewById(R.id.violation_time_title)
        private var violationTime: TextView = view.findViewById(R.id.violation_time)
        private var violationAct: TextView = view.findViewById(R.id.violation_act)
        private var violationType: TextView = view.findViewById(R.id.violation_type)
        private var violationLocationTitle: TextView = view.findViewById(R.id.violation_location_title)
        private var violationLocation: TextView = view.findViewById(R.id.violation_location)
        private var violationSum: TextView = view.findViewById(R.id.violation_sum)
        private var violationImg: ImageView = view.findViewById(R.id.violation_img)

        @SuppressLint("SetTextI18n")
        fun onBind(model: ViolationCarModel) {
            violationTime.text = model.violationTime
            violationAct.text = model.qarorSery + " " + model.qarorNumber
            violationType.text = model.violationType

//            (violationLocationTitle.height+violationLocation.height).also { violationTimeTitle.marginTop = it }

            violationLocation.text = model.location
            if (model.location.isEmpty()){
                violationLocation.visibility = View.GONE
                violationLocationTitle.visibility = View.GONE
            }
//            violationSum.text = model.sum+" "+view.context.resources.getString(R.string.sum)
            violationSum.text = getSum(model.sum.toInt())

            violationImg.setImageResource(when(model.qarorSery){
                "KV"->R.drawable.ic_policeman
                "RR"->R.drawable.ic_artist
                else->R.drawable.ic_radar
            })
        }


        private fun getSum(sum:Int):String{
            var suma = sum
            val sumStringList = mutableListOf<String>()
            val sumString = StringBuilder()

            while (suma > 1000) {
//            logd(totalSum)
                sumStringList.add(
                    (if ((suma % 1000) == 0) "000"
                    else suma % 1000).toString()
                )
                sumStringList.add(" ")
                suma /= 1000
            }
            sumStringList.add(suma.toString())

            for (item in sumStringList.size - 1 downTo 0) {
                sumString.append(sumStringList[item])
            }

            sumString.append(",00 ")
            sumString.append(view.context.getString(R.string.sum))
            return sumString.toString()
        }
    }

}

