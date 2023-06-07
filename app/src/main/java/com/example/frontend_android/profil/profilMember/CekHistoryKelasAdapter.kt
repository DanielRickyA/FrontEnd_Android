package com.example.frontend_android.profil.profilMember

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvHistoryKelasMemberBinding
import com.example.frontend_android.databinding.RvJadwalKelasBinding
import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas
import com.example.frontend_android.response.profilMember.DataHistoryKelas

class CekHistoryKelasAdapter (
    private val item: ArrayList<DataHistoryKelas>
): RecyclerView.Adapter<CekHistoryKelasAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvHistoryKelasMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataHistoryKelas) {
            with(binding) {
                tvTanggal.text = data.fJadwalHarian.tanggalJadwalHarian
                tvNamaInstruktur.text = data.fJadwalHarian.fInstruktur.nama
                tvNamaKelas.text = data.fJadwalHarian.fJadwalUmum.fKelas.nama
                tvJamKelas.text = data.fJadwalHarian.jamKelas
                tvHarga.text = data.fJadwalHarian.fJadwalUmum.fKelas.harga.toString()
                tvHari.text = data.fJadwalHarian.fJadwalUmum.hariKelas
                tvJenisDepo.text = data.jenisPembayaran
                tvWaktuPresensi.text = data.waktuPresensi
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvHistoryKelasMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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