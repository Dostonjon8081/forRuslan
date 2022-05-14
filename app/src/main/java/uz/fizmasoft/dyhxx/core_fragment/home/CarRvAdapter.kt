package uz.fizmasoft.dyhxx.core_fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.helper.db.CarEntity

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
        val carImage: ImageView = view.findViewById(R.id.rv_item_image)
//        var mark: TextView = view.findViewById(R.id.rv_item_car_mark)

        //        var texPasSer: TextView = view.findViewById(R.id.rv_item_car_tex_pass)
        private var number: TextView = view.findViewById(R.id.rv_item_car_number)
        private var numbe: TextView = view.findViewById(R.id.rv_item_car_numbe)

        private var rvItemEdit: AppCompatImageView = view.findViewById(R.id.rv_item_edit)

        fun onBind(model: CarEntity, position: Int) {
            //car number
            val numberText = model.carNumber

            if (numberIsPersonal(numberText.substring(5, 6))) {
                numbe.text = numberText.substring(0, 2)
                number.text = "${numberText.substring(2, 3)} " +
                        "${numberText.substring(3, 6)}" +
                        " ${numberText.substring(6)}"
            } else {
                numbe.text = numberText.substring(0, 2)
                number.text = "${numberText.substring(2, 5)} " +
                        "${numberText.substring(5)}"
            }

            rvItemEdit.setOnClickListener { rvItemClick!!.clickedItemEdit(list[position]) }
            view.rootView.setOnClickListener { rvItemClick!!.clickedItem(list[position]) }
//            addCarModel(model.carModel, this.itemView)
            if (model.carModel.isNotEmpty()) {
            this.model.text = model.carModel ?: "_ _ _ _ _ _ _ _ _ "
                addCarImg(view, model)
            }

            bottomMargin(position, this.itemView)
        }

        private fun addCarImg(view: View, model: CarEntity) {
            try {
                val drawableImg = view.resources.getIdentifier(model.carModel.lowercase(), "drawable", view.context.packageName)
                carImage.setImageResource(drawableImg)
            } catch (e: Exception) {
                carImage.setImageResource(R.drawable.malibu2)
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
        if (position == list.size - 1 && position < 7) {
            val params = itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 200
            itemView.layoutParams = params;
        }
    }

}