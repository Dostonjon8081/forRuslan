package com.softdata.dyhxx.core_fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.helper.db.CarEntity

class CarRvAdapter : RecyclerView.Adapter<CarRvAdapter.VH>() {

    private val list = mutableListOf<CarEntity>()
    private var rvItemClick: RvItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_home_fragment_twoo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    fun rvClickListener(rvItemClick: RvItemClick) {
        this.rvItemClick = rvItemClick
    }

    fun submitList(list: List<CarEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addCar(carEntity: CarEntity) {
        list.add(carEntity)
        notifyItemInserted(list.size)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {

        var model: TextView = view.findViewById(R.id.rv_item_car_model)
//        var texPasSer: TextView = view.findViewById(R.id.rv_item_car_tex_pass)
        var number: TextView = view.findViewById(R.id.rv_item_car_number)
        var rvItemEdit: AppCompatImageView = view.findViewById(R.id.rv_item_edit)

        fun onBind(model: CarEntity, position: Int) {
            //car number
            val numberText = model.carNumber
            val numberString =
                if (numberIsPersonal(numberText.substring(5, 6))) {
                    " ${numberText.substring(0, 2)}  ${
                        numberText.substring(2, 3).uppercase()
                    } ${numberText.substring(3, 6)} ${numberText.substring(6).uppercase()}"
                } else {
                    " ${numberText.substring(0, 2)}  ${
                        numberText.substring(2, 5)
                    } ${numberText.substring(5).uppercase()}"
                }
            number.text = numberString

            //tex pass series and number
//            texPasSer.text =
//                "${list[position].texPass.substring(0, 3)}  ${list[position].texPass.substring(3)}"

            this.model.text = model.carModel

            rvItemEdit.setOnClickListener { rvItemClick!!.clickedItemDelete(list[position].carNumber) }
            view.setOnClickListener { rvItemClick!!.clickedItem(position) }

            if (position == list.size - 1) {
                val params = this.itemView.layoutParams as RecyclerView.LayoutParams
                params.bottomMargin = 200
                this.itemView.layoutParams = params;
            }

        }
    }

    fun numberIsPersonal(substring: String): Boolean {
        return try {
            substring.toInt() is Int
        } catch (e: Exception) {
            false
        }
    }
}