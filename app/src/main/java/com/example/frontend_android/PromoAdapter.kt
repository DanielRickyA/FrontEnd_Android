package com.example.frontend_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvPromoBinding
import com.example.frontend_android.response.promo.DataItemPromo

class PromoAdapter (private val item: ArrayList<DataItemPromo>): RecyclerView.Adapter<PromoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvPromoBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(data: DataItemPromo){
                with(binding){
                    tvNamaPromo.text = data.nama
                    tvJenisPromo.text = data.jenis
                    tvMinPem.text = data.minimalPembelian.toString()
                    tvBonus.text = data.bonus.toString()
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPromoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item[position]

        holder.bind(data)
    }
}