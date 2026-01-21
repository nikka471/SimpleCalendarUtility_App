//package com.poliarc.simplecalendarutilityapp.ui.detail_event
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.poliarc.simplecalendarutilityapp.R
//
//class EventDetailActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_event_detail)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}

package com.poliarc.simplecalendarutilityapp.ui.detail_event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.poliarc.simplecalendarutilityapp.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val title = intent.getStringExtra("event_title") ?: "N/A"
        val date = intent.getStringExtra("event_date") ?: "N/A"
        val description = intent.getStringExtra("event_description") ?: "N/A"
        val notes = intent.getStringExtra("event_notes") ?: ""

        // Set data to TextViews
        binding.tvEventTitle.text = title
        binding.tvEventDate.text = date
        binding.tvEventDescription.text = description
        binding.tvEventNotes.text = if (notes.isNotEmpty()) notes else "No Notes"
    }
}

