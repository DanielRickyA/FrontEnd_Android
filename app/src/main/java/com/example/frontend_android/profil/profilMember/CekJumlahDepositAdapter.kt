package com.example.frontend_android.profil.profilMember

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvDepoKelasBinding
import com.example.frontend_android.response.profilMember.DataItem


class CekJumlahDepositAdapter (
    private val item: ArrayList<DataItem>,
): RecyclerView.Adapter<CekJumlahDepositAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RvDepoKelasBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataItem){
            with(binding){
                tvNamaKelas.text = data.fKelas.nama
                tvSisaDepo.text = data.sisaDeposit.toString()
                tvMasaBerlaku.text = data.masaBerlakuDepo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvDepoKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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