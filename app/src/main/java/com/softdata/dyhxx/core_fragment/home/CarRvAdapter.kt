package com.softdata.dyhxx.core_fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.util.logd

class CarRvAdapter : RecyclerView.Adapter<CarRvAdapter.VH>() {

    private val list = mutableListOf<CarEntity>()
    private var rvItemClick: RvItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_home_fragment, parent, false)
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
        var mark: TextView = view.findViewById(R.id.rv_item_car_mark)
        var modelTitle: TextView = view.findViewById(R.id.rv_item_car_model_title)

        //        var texPasSer: TextView = view.findViewById(R.id.rv_item_car_tex_pass)
        var number: TextView = view.findViewById(R.id.rv_item_car_number)
        var numbe: TextView = view.findViewById(R.id.rv_item_car_numbe)
        var rvItemEdit: AppCompatImageView = view.findViewById(R.id.rv_item_edit)

        fun onBind(model: CarEntity, position: Int) {
            //car number
            val numberText = model.carNumber

            if (numberIsPersonal(numberText.substring(5, 6))) {
                numbe.text = "${numberText.substring(0, 2)}"
                number.text = "${numberText.substring(2, 3)} ${
                    numberText.substring(
                        3,
                        6
                    )
                } ${numberText.substring(6)}"
            } else {
                numbe.text = "${numberText.substring(0, 2)}"
                number.text = "${numberText.substring(2, 5)} ${numberText.substring(5)}"
            }

            rvItemEdit.setOnClickListener { rvItemClick!!.clickedItemDelete(list[position].carNumber) }
            view.setOnClickListener { rvItemClick!!.clickedItem(position) }

            if (model.carMark.isNotEmpty()) mark.text = model.carMark
            addCarModel(model.carModel, this.itemView)

            bottomMargin(position, this.itemView)
        }
    }

    private fun addCarModel(model: String, view: View) {

        if (model.isNotEmpty()) {
            this.VH(view).apply {
                this.model.visibility = View.VISIBLE
                modelTitle.visibility = View.VISIBLE
                this.model.text = model
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

    fun bottomMargin(position: Int, itemView: View) {
        if (position == list.size - 1) {
            val params = itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 200
            itemView.layoutParams = params;
        }
    }
}