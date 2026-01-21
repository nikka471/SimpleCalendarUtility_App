package com.poliarc.simplecalendarutilityapp.ui.detail_event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.poliarc.simplecalendarutilityapp.R
import com.poliarc.simplecalendarutilityapp.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.horizon)

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

