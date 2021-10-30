package uz.fizmasoft.dyhxx.core_fragment.home

import uz.fizmasoft.dyhxx.helper.db.CarEntity

interface RvItemClick {
    fun clickedItemDelete(carNumber:String)

    fun clickedItem(position: Int)

    fun clickedItemEdit(carEntity: CarEntity)
}