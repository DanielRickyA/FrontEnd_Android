package com.example.frontend_android.instruktur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvDataMemberBinding
import com.example.frontend_android.response.presensiKelas.DataMember

class PresensiMemberAdapter(
    private val item: ArrayList<DataMember>,
    val callback: ClickInterface
): RecyclerView.Adapter<PresensiMemberAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: RvDataMemberBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataMember){
            with(binding){
                tvNamaKelas.text = data.fJadwalHarian.fJadwalUmum.fKelas.nama
                tvInstruktur.text = data.fJadwalHarian.fInstruktur.nama
                tvMember.text = data.fMember.nama
                tvTanggalBooking.text = data.tanggalBooking
                tvPembayaran.text = data.jenisPembayaran
                tvWaktuPresensi.text = data.waktuPresensi
                tvStatus.text = data.status

                btnHadir.setOnClickListener{
                    callback.onClickItem(data)
                }

                btnBatal.setOnClickListener{
                    callback.onClickBatal(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvDataMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onClickItem(data: DataMember)
        fun onClickBatal(data: DataMember)
    }

}