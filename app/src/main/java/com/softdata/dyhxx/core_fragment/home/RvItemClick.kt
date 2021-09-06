package com.softdata.dyhxx.core_fragment.home

import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.model.SaveCarModel

interface RvItemClick {
    fun clickedItemDelete(carNumber:String)

    fun clickedItem(position: Int)
}