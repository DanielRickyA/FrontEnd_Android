package com.example.frontend_android.instruktur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvHistoryPerizinanBinding
import com.example.frontend_android.databinding.RvJadwalInstrukturBinding
import com.example.frontend_android.response.IjinInstruktur.DataItem
import com.example.frontend_android.response.IjinInstruktur.DataItemHistory
import com.example.frontend_android.response.IjinInstruktur.ReponseHistoryPerizinan

class HistoryPerizinanAdapter(
    private val item: ArrayList<DataItemHistory>
): RecyclerView.Adapter<HistoryPerizinanAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvHistoryPerizinanBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: DataItemHistory){
            with(binding){
                tvNamaInstruktur.text = data.fInstruktur.nama
                Keterangan.text = data.keterangan
                tanggalIzin.text = data.tanggalIzin
                tanggalBuatIzin.text = data.tanggalBuatIzin
                tvTanggalKonfirm.text = data.tanggalKonfirm
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHistoryPerizinanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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