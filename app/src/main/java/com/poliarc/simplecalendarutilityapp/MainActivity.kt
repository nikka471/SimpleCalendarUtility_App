package com.poliarc.simplecalendarutilityapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
//package com.poliarc.simplecalendarutilityapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.poliarc.simplecalendarutilityapp.data.model.Event
//import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
//import com.poliarc.simplecalendarutilityapp.databinding.ActivityMainBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var eventAdapter: EventAdapter
//    private val eventList = mutableListOf<Event>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//        fetchEventsFromApi()
//
//        binding.fabAddEvent.setOnClickListener {
//            val intent = Intent(this, AddEventActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun setupRecyclerView() {
//        eventAdapter = EventAdapter(eventList) { event ->
//            // OnClick: Open EventDetailActivity
//            val intent = Intent(this, EventDetailActivity::class.java)
//            intent.putExtra("event_id", event.id)
//            startActivity(intent)
//        }
//        binding.rvEvents.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = eventAdapter
//        }
//    }
//
//    private fun fetchEventsFromApi() {
//        val api = RetrofitClient.apiService
//        api.getEvents().enqueue(object : Callback<List<Event>> {
//            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
//                if (response.isSuccessful && response.body() != null) {
//                    eventList.clear()
//                    eventList.addAll(response.body()!!)
//                    eventAdapter.notifyDataSetChanged()
//                } else {
//                    Toast.makeText(this@MainActivity, "Failed to load events", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    override fun onResume() {
//        super.onResume()
//        // Optional: refresh events when returning from AddEventActivity
//        fetchEventsFromApi()
//    }
//}

