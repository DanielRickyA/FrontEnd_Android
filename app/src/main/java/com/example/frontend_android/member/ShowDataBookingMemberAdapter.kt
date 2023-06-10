package com.example.frontend_android.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvDataBookingKelasBinding
import com.example.frontend_android.response.profilMember.DataHistoryKelas

class ShowDataBookingMemberAdapter(
    private val item: ArrayList<DataHistoryKelas>,
    var callback: ClickInterface

): RecyclerView.Adapter<ShowDataBookingMemberAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RvDataBookingKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataHistoryKelas) {
            with(binding) {
                tvNamaKelas.text = data.fJadwalHarian.fJadwalUmum.fKelas.nama
                tvTanggalBooking.text = data.fJadwalHarian.tanggalJadwalHarian
                tvInstruktur.text = data.fJadwalHarian.fInstruktur.nama
                tvJamKelas.text = data.fJadwalHarian.jamKelas
                tvHari.text = data.fJadwalHarian.fJadwalUmum.hariKelas
                tvHarga.text = data.fJadwalHarian.fJadwalUmum.fKelas.harga.toString()

                btnBatal.setOnClickListener{
                    callback.onClickItem(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvDataBookingKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item[position]
        holder.bind(data)
    }
    interface ClickInterface{
        fun onClickItem(data: DataHistoryKelas)
    }



}