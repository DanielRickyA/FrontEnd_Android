package com.example.frontend_android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.frontend_android.member.BookingGymFragment
import com.example.frontend_android.member.ShowBookingKelas
import com.example.frontend_android.member.showBookingGym
import com.example.frontend_android.profil.profilMember.ProfilMember
import com.example.frontend_android.profil.profilMember.ProfilMemberNew
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeMemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_member)
        getSupportActionBar()?.hide()

        val nav : BottomNavigationView = findViewById(R.id.bottom_navigation)


        changeFragment(TimeTablesFragment())

        nav.setOnItemSelectedListener{item -> when(item.itemId){
            R.id.home->{
                changeFragment(TimeTablesFragment())
                true
            }
            R.id.bookingkelas->{
                changeFragment(ShowBookingKelas())
                true
            }
            R.id.bookingGym->{
                changeFragment(showBookingGym())
                true
            }
            R.id.profile->{
                changeFragment(ProfilMemberNew())
                true
            }
            else->false
        }
        }
    }

    fun changeFragment(fragment: Fragment){
        if(fragment !=null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@HomeMemberActivity)
            builder.setMessage("Are you sure want to exit?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int) {
                        finishAndRemoveTask()
                    }
                })
                .show()


        }
        return super.onOptionsItemSelected(item)
    }
}