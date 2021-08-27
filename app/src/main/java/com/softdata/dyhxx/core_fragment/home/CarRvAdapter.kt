package com.softdata.dyhxx.core_fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.helper.db.CarEntity

class CarRvAdapter(val list: List<CarEntity>, private val rvItemClick: RvItemClick) : RecyclerView.Adapter<CarRvAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_home_fragment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.apply {

            //car number
            val numberText = list[position].carNumber
            val numberString =
                if (numberIsPersonal(numberText.substring( 5,6))) {
                    " ${numberText.substring(0, 2)}  ${numberText.substring(2, 3).uppercase()} ${numberText.substring(3, 6)} ${numberText.substring(6).uppercase()}"
                } else {
                    " ${numberText.substring(0, 2)}  ${numberText.substring(2,5)} ${numberText.substring(5).uppercase()}"
                }
            number.text = numberString

            //tex pass series and number
            texPasSer.text = "${list[position].texPass.substring(0,3)}  ${list[position].texPass.substring(3)}"

            model.text = list[position].carModel

            rvItemEdit.setOnClickListener { rvItemClick.clickedItemDelete(list[position].id.toInt()) }
            this.view.setOnClickListener { rvItemClick.clickedItem(position) }
        }
    }

    override fun getItemCount() = list.size


    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var model: TextView = view.findViewById(R.id.rv_item_car_model)
        var texPasSer: TextView = view.findViewById(R.id.rv_item_car_tex_pass)
        var number: TextView = view.findViewById(R.id.rv_item_car_number)
        var rvItemEdit: AppCompatImageView = view.findViewById(R.id.rv_item_edit)
    }


    private fun numberIsPersonal(substring: String): Boolean {
        return try {
            substring.toInt() is Int
        } catch (e: Exception) {
            false
        }
    }
}