package com.example.frontend_android.instruktur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvJadwalKelasInstrukturBinding
import com.example.frontend_android.response.presensiKelas.DataItem


class JadwalKelasInstrukturAdapter(
    private val item: ArrayList<DataItem>
):  RecyclerView.Adapter<JadwalKelasInstrukturAdapter.ViewHolder>() {
    private lateinit var callback: ClickInterface

    inner class ViewHolder(val binding: RvJadwalKelasInstrukturBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataItem){
            with(binding){
                tvNamaKelas.text = data.fJadwalUmum.fKelas.nama
                tvTanggalKelas.text = data.tanggalJadwalHarian
                tvJamKelas.text = data.fJadwalUmum.jamKelas
                tvHari.text = data.fJadwalUmum.hariKelas
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvJadwalKelasInstrukturBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item[position]

        holder.bind(data)
        holder.itemView.setOnClickListener{
            callback.onClickItem(data)
        }
    }

    interface ClickInterface{
        fun onClickItem(data: DataItem)
    }

    fun setCallback(callback: ClickInterface){
        this.callback = callback
    }
}