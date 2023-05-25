package com.example.caloriestracker

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.caloriestracker.databinding.ActivityMainBinding
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var shareButton: Button
    lateinit var cameraButton: Button
    val REQUEST_IMAGE_CAPTURE = 100
    var randomCalories = 0
    lateinit var defaultCalories: TextView
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        defaultCalories = findViewById(R.id.textView2)
        val textFromActivity = intent.getStringExtra("Home")
        if (textFromActivity != null) {
            if (textFromActivity.toInt()>0){
                defaultCalories.setText(textFromActivity.toString())
            }
        }
        binding.apply{
            toggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.firstItem->{
                        goToHome(defaultCalories.text.toString())
                    }
                    R.id.secondItem->{
                        goToSearchBarActivity(defaultCalories.text.toString())
                    }
                    R.id.thirdItem->{
                        goToLogin(defaultCalories.text.toString())
                    }
                    R.id.fourthItem->{
                        goToMap(defaultCalories.text.toString())
                    }
                    R.id.fifthItem->{
                        goToVideo(defaultCalories.text.toString())
                    }
                }
                true
            }
        }

        val button = findViewById<Button>(R.id.button)
        val addCalories = findViewById<EditText>(R.id.editTextTextPersonName)
        button.setOnClickListener(){
            val val1 = defaultCalories.text.toString().toInt()
            val val2 = addCalories.text.toString().toInt()
            val result = val1+val2
            defaultCalories.setText(result.toString())
        }
        shareButton=findViewById<Button>(R.id.shareButton)
        shareButton.setOnClickListener(){
            val text = "Today i ate "+defaultCalories.text.toString()+" calories!!!"
            val intent = Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.type="text/plain"
            val chooser = Intent.createChooser(intent,"Share using...")
            startActivity(chooser)
        }

        cameraButton = findViewById(R.id.cameraButton)
        cameraButton.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try{
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }catch (e:ActivityNotFoundException){
                Toast.makeText(this,"Error: "+e.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
        image = findViewById(R.id.image);
        image.setOnClickListener(MyOnClickListener);
    }

    var MyOnClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            rotateit(v!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            randomCalories = Random.nextInt(0,1000)
            val var1= defaultCalories.text.toString().toInt()
            val result= var1+randomCalories
            defaultCalories.setText(result.toString())

        }else{
        super.onActivityResult(requestCode, resultCode, data)
    }}

    private fun goToSearchBarActivity(extra: String) {
        val intent = Intent(this, AddBySearchBar::class.java)
        intent.putExtra("SearchBarActivity", extra)
        startActivity(intent)
    }

    private fun goToHome(extra: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Home", extra)
        startActivity(intent)
    }

    private fun goToLogin(extra: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("Login", extra)
        startActivity(intent)
    }

    private fun goToMap(extra: String) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("Map", extra)
        startActivity(intent)
    }

    private fun goToVideo(extra: String) {
        val intent = Intent(this, HealthVideo::class.java)
        intent.putExtra("Video", extra)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rotateit(viewToFlip: View) {
        val rot = ObjectAnimator.ofFloat(viewToFlip, "rotation", 0f, 360f)
        rot.duration = 3000
        rot.start()
    }
}