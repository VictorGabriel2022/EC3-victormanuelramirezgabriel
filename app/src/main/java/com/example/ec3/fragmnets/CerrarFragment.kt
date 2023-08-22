package com.example.ec3.fragmnets

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ec3.LoginActivity
import com.example.ec3.R
import com.example.ec3.SplashScreenActivity
import com.example.ec3.databinding.FragmentCerrarBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CerrarFragment : Fragment() {
    private lateinit var binding: FragmentCerrarBinding
    private lateinit var sharedPreferences: SharedPreferences
   private  lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            LoginActivity.SESSION_PREFERENCE,
            AppCompatActivity.MODE_PRIVATE)
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCerrarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button2.setOnClickListener {
              firebaseAuth.signOut()
            sharedPreferences.edit().apply(){
                putString(LoginActivity.EMAIL,"").apply()
            }
            val intent=Intent(requireActivity(),SplashScreenActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }


    }


}