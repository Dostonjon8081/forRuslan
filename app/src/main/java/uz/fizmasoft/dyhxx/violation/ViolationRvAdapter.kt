package uz.fizmasoft.dyhxx.violation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.databinding.ItemRvViolationBinding

class ViolationRvAdapter : RecyclerView.Adapter<ViolationRvAdapter.VH>() {

    private val list = mutableListOf<ViolationCarModel>()
    private var violationRvClick: ClickViolationRv? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRvViolationBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
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

    fun rvClick(clickViolationRv: ClickViolationRv) {
        this.violationRvClick = clickViolationRv
    }

    inner class VH(private val binding: ItemRvViolationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(model: ViolationCarModel) = with(binding) {
//            violationTime.text = dateFormatter.parse(model.violationTime.split("T").toString()).toString()
            violationTime.text = swapDate(model.violationTime.split("T")[0])
            violationTimeHour.text = cutViolationHour(model.violationTime.split("T")[1])
            violationAct.text = model.qarorSery + " " + model.qarorNumber
            violationType.text = model.violationType

//            violationLocation.text = model.location
//            if (model.location.isEmpty()) {
//                violationLocation.visibility = View.GONE
//                violationLocationTitle.visibility = View.GONE
//            }

            violationSum.text = getSum(model.sum.toInt())

            violationImg.setImageResource(
                when (model.qarorSery) {
                    "KV" -> R.drawable.ic_policeman
                    "RR" -> R.drawable.ic_artist
                    else -> R.drawable.ic_radar
                }
            )

            root.setOnClickListener {
                violationRvClick?.violationDetail(model)
            }
            /* if (model.qarorSery != "KV") {
 //                violationSaveFile.visibility = View.VISIBLE
                 root.setOnClickListener {
                     violationRvClick?.violationFileID(
                         model.id.toString(),
                         model.qarorSery + model.qarorNumber
                     )
                 }
             }*/
        }

        private fun cutViolationHour(hour: String): String {
            val splitHour = hour.split(":")
            return "${splitHour[0]}:${splitHour[1]}:00"
        }

        private fun swapDate(date: String): String {
            val splitDate = date.split("-")
            return "${splitDate[2]}.${splitDate[1]}.${splitDate[0]}"
        }

        private fun getSum(sum: Int): String {
            var suma = sum
            val sumStringList = mutableListOf<String>()
            val sumString = StringBuilder()

            while (suma > 1000) {
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
//            sumString.append(binding.root.context.getString(R.string.sum))
            return sumString.toString()
        }
    }

}

