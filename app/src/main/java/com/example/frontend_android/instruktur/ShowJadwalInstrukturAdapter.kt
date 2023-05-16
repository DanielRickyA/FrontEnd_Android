package com.example.frontend_android.instruktur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvJadwalInstrukturBinding
import com.example.frontend_android.response.IjinInstruktur.DataItem

class ShowJadwalInstrukturAdapter(
    private val item: ArrayList<DataItem>
): RecyclerView.Adapter<ShowJadwalInstrukturAdapter.ViewHolder>() {

    private lateinit var callback: ClickInterface


    inner class ViewHolder(val binding: RvJadwalInstrukturBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: DataItem){
            with(binding){
                tvTanggal.text = data.tanggalJadwalHarian
                tvInstruktur.text = data.fInstruktur.nama
                tvHari.text = data.fJadwalUmum.hariKelas
                tvSesi.text = data.fJadwalUmum.jamKelas

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvJadwalInstrukturBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onClickItem(data: DataItem)
    }

    fun setCallback(callback: ClickInterface){
        this.callback = callback
    }


}