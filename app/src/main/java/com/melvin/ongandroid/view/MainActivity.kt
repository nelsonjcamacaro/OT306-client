package com.melvin.ongandroid.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.view.fragment.ActivitiesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val activitiesFragment = ActivitiesFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, homeFragment)
            commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navContact -> {Toast.makeText(this@MainActivity, "Contacto", Toast.LENGTH_SHORT).show()
                    true}
                R.id.navHome->{ supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, homeFragment)
                    commit()}
                    true}
                R.id.navNews-> {Toast.makeText(this@MainActivity, "Novedades", Toast.LENGTH_SHORT).show()
                    true}
                R.id.navTestimonials->{ supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, activitiesFragment)
                        commit()
                    }
                    true
                }
                R.id.navStaff->{Toast.makeText(this@MainActivity, "Nosotros", Toast.LENGTH_SHORT).show()
                    true}
                else -> false
            }
        }
    }
}