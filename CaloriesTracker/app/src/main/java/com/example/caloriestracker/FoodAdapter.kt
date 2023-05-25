package com.example.caloriestracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodAdapter(var mList: List<FoodData>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    public var totalCalories: Int = 0
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.picId)
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val calories: TextView = itemView.findViewById(R.id.calories)
        val button: FloatingActionButton = itemView.findViewById(R.id.buttonAddCalories)
    }

    fun setFilteredList(mList: List<FoodData>){
        this.mList= mList;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].logo)
        holder.titleTv.text=mList[position].title
        holder.calories.text=mList[position].calories.toString()
        holder.button.setOnClickListener(){
            this.totalCalories=this.totalCalories + mList[position].calories
            print(this.totalCalories)
        }
    }
}