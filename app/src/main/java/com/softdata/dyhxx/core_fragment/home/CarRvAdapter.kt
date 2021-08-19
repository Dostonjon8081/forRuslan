package com.softdata.dyhxx.core_fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.helper.util.db.CarEntity

class CarRvAdapter(val list: List<CarEntity>) : RecyclerView.Adapter<CarRvAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_home_fragment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {

    }
}