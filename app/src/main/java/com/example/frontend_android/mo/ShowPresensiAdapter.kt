package com.example.frontend_android.mo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvJadwalKelasBinding
import com.example.frontend_android.databinding.RvPresensiBinding

import com.example.frontend_android.response.presensiInstruktur.DataItemPresensi

class ShowPresensiAdapter (
    private val item: ArrayList<DataItemPresensi>
): RecyclerView.Adapter<ShowPresensiAdapter.ViewHolder>() {

    private lateinit var callback: ShowPresensiAdapter.ClickInterface

    inner class ViewHolder(val binding: RvPresensiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemPresensi) {
            with(binding) {
                tvNamaInstruktur.text = data.fInstruktur.nama
                tvTanggal.text = data.tanggalKelas
                tvJamMulai.text = data.jamMulai
                tvJamSelesai.text = data.jamSelesai
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvPresensiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item[position]

        holder.bind(data)
        holder.itemView.setOnClickListener{
            callback.onClickItem(data)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
    interface ClickInterface{
        fun onClickItem(data: DataItemPresensi)
    }

    fun setCallback(callback: ClickInterface){
        this.callback = callback
    }
}