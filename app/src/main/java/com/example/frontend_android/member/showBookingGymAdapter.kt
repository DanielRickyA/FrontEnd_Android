package com.example.frontend_android.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.databinding.RvBookingGymBinding
import com.example.frontend_android.databinding.RvJadwalKelasBinding
import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas
import com.example.frontend_android.response.bookingGym.DataItem
import com.example.frontend_android.response.bookingGym.ResponseShowBookingGym

class showBookingGymAdapter(
    private val item: ArrayList<DataItem>,
    var callback: ClickInterface
): RecyclerView.Adapter<showBookingGymAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RvBookingGymBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataItem){
            with(binding){
                tvTanggalKapanBooking.text = data.tanggalYangDibooking
                tvTanggalBooking.text = data.tanggalBooking
                tvSlotWaktu.text = data.slotWaktu
                tvJamPresensi.text = data.jamPresensi

                btnBatal.setOnClickListener{
                    callback.onClickItem(data)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvBookingGymBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun onClickItem(data: DataItem)
    }



}