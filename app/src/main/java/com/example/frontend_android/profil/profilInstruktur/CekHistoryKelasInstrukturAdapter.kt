package com.example.frontend_android.profil.profilInstruktur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvHistoryKelasInstrukturBinding
import com.example.frontend_android.databinding.RvHistoryPerizinanBinding
import com.example.frontend_android.response.IjinInstruktur.DataItemHistory
import com.example.frontend_android.response.profilInstruktur.DataItem

class CekHistoryKelasInstrukturAdapter(
    private val item: ArrayList<DataItem>
): RecyclerView.Adapter<CekHistoryKelasInstrukturAdapter.ViewHolder>()  {
    inner class ViewHolder(val binding: RvHistoryKelasInstrukturBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: DataItem){
            with(binding){
                tvTanggal.text = data.tanggalKelas
                tvNamaKelas.text = data.fJadwalHarian.fJadwalUmum.fKelas.nama
                tvInstruktur.text = data.fInstruktur.nama
                tvDurasiKelas.text = data.durasiKelas.toString()
                tvJamMulai.text = data.jamMulai
                tvJamSelesai.text = data.jamSelesai
                tvDurasiTerlambat.text = data.durasiTerlambat.toString()
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CekHistoryKelasInstrukturAdapter.ViewHolder {
        val binding = RvHistoryKelasInstrukturBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CekHistoryKelasInstrukturAdapter.ViewHolder,
        position: Int
    ) {
        val data = item[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}