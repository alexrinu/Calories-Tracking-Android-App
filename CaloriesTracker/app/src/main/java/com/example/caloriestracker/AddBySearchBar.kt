package com.example.caloriestracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.ButtonBarLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriestracker.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class AddBySearchBar : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FoodData>()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_by_search_bar)
        val textFromActivity1 = intent.getStringExtra("SearchBarActivity")

        val defaultCalories = findViewById<TextView>(R.id.textSearchBar1)
        defaultCalories.setText(textFromActivity1)

        recyclerView=findViewById(R.id.recyclerView)
        searchView=findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this)

        addDataToList()
        adapter= FoodAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }

        })

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener(){
            goToHome(defaultCalories.text.toString())
        }

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener(){
            val val1 = defaultCalories.text.toString().toInt()
            val val2 = adapter.totalCalories.toString().toInt()
            val result = val1+val2
            defaultCalories.setText(result.toString())
        }

    }

    private fun goToHome(extra: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Home", extra)
        startActivity(intent)
    }

    private fun filterList(query: String){
        if (query!=null){
            val filteredList = ArrayList<FoodData>()
            for (i in mList){
                if (i.title.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            }else{
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToCaloriesList(){

    }

    private fun addDataToList(){
        mList.add(FoodData("Milka Chocolate",R.drawable.milka, 180))
        mList.add(FoodData("Pizza Margherita",R.drawable.margherita,751))
        mList.add(FoodData("Pizza Diavola",R.drawable.diavola,921))
        mList.add(FoodData("Coca-Cola",R.drawable.cola,245))
        mList.add(FoodData("Big Mac",R.drawable.burger,656))
        mList.add(FoodData("French fries",R.drawable.fries,350))
        mList.add(FoodData("White rice",R.drawable.rice,200))
        mList.add(FoodData("Pizza Formaggi",R.drawable.formaggi,941))
        mList.add(FoodData("Caesar Salad",R.drawable.salad,350))
        mList.add(FoodData("Chicken Breast",R.drawable.chicken,278))
    }
}