package uz.fizmasoft.dyhxx.core_fragment.home

import uz.fizmasoft.dyhxx.helper.db.CarEntity

interface RvItemClick {
//    fun clickedItemDelete(carNumber:String)

    fun clickedItem(carEntity: CarEntity)

    fun clickedItemEdit(carEntity: CarEntity)
}