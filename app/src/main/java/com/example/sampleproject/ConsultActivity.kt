package com.example.sampleproject

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.sample.doctorproject2.databinding.ActivityConsultBinding
import ir.sample.doctorproject2.databinding.ActivityDoctorBinding

class ConsultActivity : AppCompatActivity() {
    lateinit var binding : ActivityConsultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        var id = intent.getIntExtra("id" , -1)
        if (id == -1){
            binding.textViewDoctorCalls.text = "ٔدکتر شما پیدا نشد"
        }else {
            var myDoctor = Hospital.getDoctor(id)
            binding.textViewDoctorCalls.text = "دکتر ${myDoctor?.name} با شما تماس خواهد گرفت"
            binding.buttonDrCall.isEnabled  = myDoctor?.onlineStatus == OnlineStatus.Online
        }
        binding.buttonDrCall.setOnClickListener {
            Toast.makeText(this , "الان دکتر بهت زنگ می زنه" , Toast.LENGTH_SHORT).show()
            var username = binding.editTextName.text.toString()
            var userTel = binding.editTextTel.text.toString()

            saveInShared(username , userTel)
        }
        if (!getFromShared_name().isNullOrEmpty() ){
            binding.editTextName.visibility = View.GONE
        }
        if (!getFromShared_tel().isNullOrEmpty() ){
            binding.editTextTel.visibility = View.GONE
        }
    }

    private fun saveInShared(username: String, userTel: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name" , username)
        editor.putString("tel" , userTel)
        editor.apply()
    }
    private fun getFromShared_name() : String?{
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        var name = sharedPreferences.getString("name" , "")
        return name
    }
    private fun getFromShared_tel() : String?{
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        var tel = sharedPreferences.getString("tel" , "")
        return tel
    }
}