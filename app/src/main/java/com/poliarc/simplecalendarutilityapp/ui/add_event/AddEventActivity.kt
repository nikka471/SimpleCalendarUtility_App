package com.poliarc.simplecalendarutilityapp.ui.add_event

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.poliarc.simplecalendarutilityapp.R
import com.poliarc.simplecalendarutilityapp.data.model.Event

class AddEventActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        window.statusBarColor = ContextCompat.getColor(this, R.color.horizon)

        firestore = FirebaseFirestore.getInstance()
        analytics = FirebaseAnalytics.getInstance(this)

        val etTitle = findViewById<EditText>(R.id.etEventTitle)
        val etDate = findViewById<EditText>(R.id.etEventDate)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSaveEvent)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val date = etDate.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Title & Date are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event = Event(
                id = System.currentTimeMillis().toString(),
                title = title,
                date = date,
                description = "User added event",
                notes = notes
            )

            firestore.collection("events")
                .add(event)
                .addOnSuccessListener {
                    Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show()
                    logEventAdded(event)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("FIRESTORE_ERROR", e.message.toString(), e)
                    Toast.makeText(
                        this,
                        "Failed to add event: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }


    private fun logEventAdded(event: Event) {
        val bundle = Bundle()
        bundle.putString("event_id", event.id)
        bundle.putString("event_title", event.title)
        analytics.logEvent("event_added", bundle)
    }
}

