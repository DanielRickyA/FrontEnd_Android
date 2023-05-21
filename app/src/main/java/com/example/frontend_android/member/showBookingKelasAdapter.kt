package com.example.frontend_android.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.frontend_android.databinding.RvJadwalKelasBinding

import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas

class showBookingKelasAdapter(
    private val item: ArrayList<DataItemBookingKelas>
): RecyclerView.Adapter<showBookingKelasAdapter.ViewHolder>() {

    private lateinit var callback: ClickInterface

    inner class ViewHolder(val binding: RvJadwalKelasBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataItemBookingKelas){
            with(binding){
                tvTanggal.text = data.tanggalJadwalHarian
                tvNamaInstruktur.text = data.fInstruktur.nama
                tvNamaKelas.text = data.fJadwalUmum.fKelas.nama
                tvJamKelas.text = data.jamKelas
                tvStatus.text = data.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvJadwalKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onClickItem(data: DataItemBookingKelas)
    }

    fun setCallback(callback: ClickInterface){
        this.callback = callback
    }


}