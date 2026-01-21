//package com.poliarc.simplecalendarutilityapp.ui.home
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.poliarc.simplecalendarutilityapp.R
//
//class HomeActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_home)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}

//package com.poliarc.simplecalendarutilityapp.ui.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.poliarc.simplecalendarutilityapp.data.model.Event
//import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
//import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
//import com.poliarc.simplecalendarutilityapp.databinding.ActivityHomeBinding
//import com.poliarc.simplecalendarutilityapp.ui.add_event.AddEventActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class HomeActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityHomeBinding
//    private lateinit var adapter: EventAdapter
//    private val eventList = mutableListOf<Event>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//        fetchEventsFromApi()
//
//        // FAB → Add Event screen
//        binding.fabAddEvent.setOnClickListener {
//            startActivity(Intent(this, AddEventActivity::class.java))
//        }
//    }
//
//    private fun setupRecyclerView() {
//        adapter = EventAdapter(eventList) { event ->
//            // Later: EventDetailActivity open hoga
//            Toast.makeText(this, event.title, Toast.LENGTH_SHORT).show()
//        }
//
//        binding.rvEvents.layoutManager = LinearLayoutManager(this)
//        binding.rvEvents.adapter = adapter
//    }
//
//    private fun fetchEventsFromApi() {
//        RetrofitClient.apiService.getEvents()
//            .enqueue(object : retrofit2.Callback<EventResponse> {
//
//                override fun onResponse(
//                    call: retrofit2.Call<EventResponse>,
//                    response: retrofit2.Response<EventResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        val events = response.body()!!.events  // EventResponse has a List<Event>
//                        eventList.clear()
//                        eventList.addAll(events)
//                        adapter.notifyDataSetChanged()
//                    } else {
//                        Toast.makeText(
//                            this@HomeActivity,
//                            "Failed to load events",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: retrofit2.Call<EventResponse>, t: Throwable) {
//                    Toast.makeText(
//                        this@HomeActivity,
//                        "Error: ${t.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//    }
//
//}

//
//package com.poliarc.simplecalendarutilityapp.ui.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.firestore.FirebaseFirestore
//import com.poliarc.simplecalendarutilityapp.data.model.Event
//import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
//import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
//import com.poliarc.simplecalendarutilityapp.databinding.ActivityHomeBinding
//import com.poliarc.simplecalendarutilityapp.ui.add_event.AddEventActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HomeActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityHomeBinding
//    private lateinit var adapter: EventAdapter
//    private val eventList = mutableListOf<Event>()
//
//    private val firestore = FirebaseFirestore.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//
//        // 🔥 IMPORTANT: dono sources call karo
//        fetchEventsFromApi()
//        fetchEventsFromFirestore()
//
//        // FAB → Add Event screen
//        binding.fabAddEvent.setOnClickListener {
//            startActivity(Intent(this, AddEventActivity::class.java))
//        }
//    }
//
//    private fun setupRecyclerView() {
//        adapter = EventAdapter(eventList) { event ->
//            Toast.makeText(this, event.title, Toast.LENGTH_SHORT).show()
//        }
//
//        binding.rvEvents.layoutManager = LinearLayoutManager(this)
//        binding.rvEvents.adapter = adapter
//    }
//
//    // ================= API EVENTS =================
//    private fun fetchEventsFromApi() {
//        RetrofitClient.apiService.getEvents()
//            .enqueue(object : Callback<EventResponse> {
//
//                override fun onResponse(
//                    call: Call<EventResponse>,
//                    response: Response<EventResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        val apiEvents = response.body()!!.events
//                        eventList.addAll(apiEvents)
//                        adapter.notifyDataSetChanged()
//                    } else {
//                        Toast.makeText(
//                            this@HomeActivity,
//                            "Failed to load API events",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                    Toast.makeText(
//                        this@HomeActivity,
//                        "API Error: ${t.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//    }
//
//    // ================= FIRESTORE EVENTS =================
//    private fun fetchEventsFromFirestore() {
//        firestore.collection("events")
//            .get()
//            .addOnSuccessListener { documents ->
//                for (doc in documents) {
//                    val event = Event(
//                        id = doc.id,
//                        title = doc.getString("title") ?: "",
//                        date = doc.getString("date") ?: "",
//                        description = doc.getString("description")
//                            ?: doc.getString("notes") ?: ""
//                    )
//                    eventList.add(event)
//                }
//                adapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener {
//                Toast.makeText(
//                    this,
//                    "Failed to load Firestore events",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }
//}


//package com.poliarc.simplecalendarutilityapp.ui.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.firestore.FirebaseFirestore
//import com.poliarc.simplecalendarutilityapp.data.model.Event
//import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
//import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
//import com.poliarc.simplecalendarutilityapp.databinding.ActivityHomeBinding
//import com.poliarc.simplecalendarutilityapp.ui.add_event.AddEventActivity
//import com.poliarc.simplecalendarutilityapp.ui.detail_event.EventDetailActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HomeActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityHomeBinding
//    private lateinit var adapter: EventAdapter
//    private val eventList = mutableListOf<Event>()
//
//    private val firestore = FirebaseFirestore.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//        setupSwipeRefresh()
//
//        loadAllEvents()
//
//        binding.fabAddEvent.setOnClickListener {
//            startActivity(Intent(this, AddEventActivity::class.java))
//        }
//    }
//
//    private fun setupRecyclerView() {
//        // In HomeActivity - inside setupRecyclerView() click listener
//        adapter = EventAdapter(eventList) { event ->
//            // Toast bhi dikhana
//            Toast.makeText(this, event.title, Toast.LENGTH_SHORT).show()
//
//            // Open EventDetailActivity with data
//            val intent = Intent(this, EventDetailActivity::class.java)
//            intent.putExtra("event_id", event.id)
//            intent.putExtra("event_title", event.title)
//            intent.putExtra("event_date", event.date)
//            intent.putExtra("event_description", event.description)
//            intent.putExtra("event_notes", event.notes ?: "")
//            startActivity(intent)
//        }
//
//
//        binding.rvEvents.layoutManager = LinearLayoutManager(this)
//        binding.rvEvents.adapter = adapter
//    }
//
//    // ================= Swipe Refresh =================
//    private fun setupSwipeRefresh() {
//        binding.swipeRefresh.setOnRefreshListener {
//            loadAllEvents()
//        }
//    }
//
//    private fun loadAllEvents() {
//        eventList.clear()
//        adapter.notifyDataSetChanged()
//
//        fetchEventsFromApi()
//        fetchEventsFromFirestore()
//    }
//
//    // ================= API EVENTS =================
//    private fun fetchEventsFromApi() {
//        RetrofitClient.apiService.getEvents()
//            .enqueue(object : Callback<EventResponse> {
//
//                override fun onResponse(
//                    call: Call<EventResponse>,
//                    response: Response<EventResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        eventList.addAll(response.body()!!.events)
//                        adapter.notifyDataSetChanged()
//                    }
//                    binding.swipeRefresh.isRefreshing = false
//                }
//
//                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                    binding.swipeRefresh.isRefreshing = false
//                    Toast.makeText(this@HomeActivity, "API Error", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    // ================= FIRESTORE EVENTS =================
//    private fun fetchEventsFromFirestore() {
//        firestore.collection("events")
//            .get()
//            .addOnSuccessListener { documents ->
//                for (doc in documents) {
//                    val event = Event(
//                        id = doc.id,
//                        title = doc.getString("title") ?: "",
//                        date = doc.getString("date") ?: "",
//                        description = doc.getString("description")
//                            ?: doc.getString("notes") ?: ""
//                    )
//                    eventList.add(event)
//                }
//                adapter.notifyDataSetChanged()
//                binding.swipeRefresh.isRefreshing = false
//            }
//            .addOnFailureListener {
//                binding.swipeRefresh.isRefreshing = false
//                Toast.makeText(this, "Firestore load failed", Toast.LENGTH_SHORT).show()
//            }
//    }
//}


//package com.poliarc.simplecalendarutilityapp.ui.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.firestore.FirebaseFirestore
//import com.poliarc.simplecalendarutilityapp.data.model.Event
//import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
//import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
//import com.poliarc.simplecalendarutilityapp.databinding.ActivityHomeBinding
//import com.poliarc.simplecalendarutilityapp.ui.add_event.AddEventActivity
//import com.poliarc.simplecalendarutilityapp.ui.detail_event.EventDetailActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HomeActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityHomeBinding
//    private lateinit var adapter: EventAdapter
//    private val eventList = mutableListOf<Event>()
//
//    private val firestore = FirebaseFirestore.getInstance()
//    private lateinit var firebaseAnalytics: FirebaseAnalytics
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        logAppOpen()
//
//        setupRecyclerView()
//        setupSwipeRefresh()
//
//        loadAllEvents()
//
//        // FAB → Add Event screen
//        binding.fabAddEvent.setOnClickListener {
//            startActivity(Intent(this, AddEventActivity::class.java))
//        }
//    }
//
//    private fun setupRecyclerView() {
//        adapter = EventAdapter(eventList) { event ->
//            // Toast dikhaye
//            Toast.makeText(this, event.title, Toast.LENGTH_SHORT).show()
//
//            // Log Event Viewed
//            logEventViewed(event)
//
//            // Open EventDetailActivity
//            val intent = Intent(this, EventDetailActivity::class.java).apply {
//                putExtra("event_id", event.id)
//                putExtra("event_title", event.title)
//                putExtra("event_date", event.date)
//                putExtra("event_description", event.description)
//                putExtra("event_notes", event.notes ?: "")
//            }
//            startActivity(intent)
//        }
//
//        binding.rvEvents.layoutManager = LinearLayoutManager(this)
//        binding.rvEvents.adapter = adapter
//    }
//
//    // ================= Swipe Refresh =================
//    private fun setupSwipeRefresh() {
//        binding.swipeRefresh.setOnRefreshListener {
//            loadAllEvents()
//        }
//    }
//
//    private fun loadAllEvents() {
//        eventList.clear()
//        adapter.notifyDataSetChanged()
//
//        fetchEventsFromApi()
//        fetchEventsFromFirestore()
//    }
//
//    // ================= API EVENTS =================
//    private fun fetchEventsFromApi() {
//        RetrofitClient.apiService.getEvents()
//            .enqueue(object : Callback<EventResponse> {
//                override fun onResponse(
//                    call: Call<EventResponse>,
//                    response: Response<EventResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        eventList.addAll(response.body()!!.events)
//                        adapter.notifyDataSetChanged()
//                    }
//                    binding.swipeRefresh.isRefreshing = false
//                }
//
//                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                    binding.swipeRefresh.isRefreshing = false
//                    Toast.makeText(this@HomeActivity, "API Error", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    // ================= FIRESTORE EVENTS =================
//    private fun fetchEventsFromFirestore() {
//        firestore.collection("events")
//            .get()
//            .addOnSuccessListener { documents ->
//                for (doc in documents) {
//                    val event = Event(
//                        id = doc.id,
//                        title = doc.getString("title") ?: "",
//                        date = doc.getString("date") ?: "",
//                        description = doc.getString("description") ?: doc.getString("notes") ?: ""
//                    )
//                    eventList.add(event)
//                }
//                adapter.notifyDataSetChanged()
//                binding.swipeRefresh.isRefreshing = false
//            }
//            .addOnFailureListener {
//                binding.swipeRefresh.isRefreshing = false
//                Toast.makeText(this, "Firestore load failed", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    // ================= Firebase Analytics =================
//    private fun logAppOpen() {
//        val bundle = Bundle()
//        bundle.putString(FirebaseAnalytics.Param.METHOD, "app_open")
//        firebaseAnalytics.logEvent("app_open", bundle)
//    }
//
//    private fun logEventViewed(event: Event) {
//        val bundle = Bundle()
//        bundle.putString("event_id", event.id)
//        bundle.putString("event_title", event.title)
//        firebaseAnalytics.logEvent("event_viewed", bundle)
//    }
//}


package com.poliarc.simplecalendarutilityapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.poliarc.simplecalendarutilityapp.R
import com.poliarc.simplecalendarutilityapp.auth.LoginActivity
import com.poliarc.simplecalendarutilityapp.data.model.Event
import com.poliarc.simplecalendarutilityapp.data.model.EventResponse
import com.poliarc.simplecalendarutilityapp.data.remote.RetrofitClient
import com.poliarc.simplecalendarutilityapp.databinding.ActivityHomeBinding
import com.poliarc.simplecalendarutilityapp.ui.add_event.AddEventActivity
import com.poliarc.simplecalendarutilityapp.ui.detail_event.EventDetailActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: EventAdapter
    private val eventList = mutableListOf<Event>()

    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar as ActionBar
        setSupportActionBar(binding.toolbar)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        logAppOpen()

        setupRecyclerView()
        setupSwipeRefresh()
        loadAllEvents()

        // FAB → Add Event screen
        binding.fabAddEvent.setOnClickListener {
            startActivity(Intent(this, AddEventActivity::class.java))
        }
    }

    // ================= RecyclerView =================
    private fun setupRecyclerView() {
        adapter = EventAdapter(eventList) { event ->
            Toast.makeText(this, event.title, Toast.LENGTH_SHORT).show()
            logEventViewed(event)

            val intent = Intent(this, EventDetailActivity::class.java).apply {
                putExtra("event_id", event.id)
                putExtra("event_title", event.title)
                putExtra("event_date", event.date)
                putExtra("event_description", event.description)
                putExtra("event_notes", event.notes ?: "")
            }
            startActivity(intent)
        }

        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter
    }

    // ================= Swipe Refresh =================
    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadAllEvents()
        }
    }

    private fun loadAllEvents() {
        eventList.clear()
        adapter.notifyDataSetChanged()
        fetchEventsFromApi()
        fetchEventsFromFirestore()
    }

    // ================= API EVENTS =================
    private fun fetchEventsFromApi() {
        RetrofitClient.apiService.getEvents()
            .enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        eventList.addAll(response.body()!!.events)
                        adapter.notifyDataSetChanged()
                    }
                    binding.swipeRefresh.isRefreshing = false
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this@HomeActivity, "API Error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // ================= FIRESTORE EVENTS =================
    private fun fetchEventsFromFirestore() {
        firestore.collection("events")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val event = Event(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        date = doc.getString("date") ?: "",
                        description = doc.getString("description") ?: doc.getString("notes") ?: ""
                    )
                    eventList.add(event)
                }
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
            .addOnFailureListener {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(this, "Firestore load failed", Toast.LENGTH_SHORT).show()
            }
    }

    // ================= Firebase Analytics =================
    private fun logAppOpen() {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.METHOD, "app_open")
        firebaseAnalytics.logEvent("app_open", bundle)
    }

    private fun logEventViewed(event: Event) {
        val bundle = Bundle()
        bundle.putString("event_id", event.id)
        bundle.putString("event_title", event.title)
        firebaseAnalytics.logEvent("event_viewed", bundle)
    }

    // ================= Toolbar Menu =================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu) // matches your XML file
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                redirectToLogin()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


private fun redirectToLogin() {

    // 🔴 YE LINE MISS THI
    com.google.firebase.auth.FirebaseAuth.getInstance().signOut()

    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
}



}
