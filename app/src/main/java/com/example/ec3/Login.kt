package com.example.ec3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent
import android.content.SharedPreferences
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.example.ec3.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        sharedPreferences = getSharedPreferences(SESSION_PREFERENCE, MODE_PRIVATE)
        // Inicialización de Firebase Authentication
        firebaseAuth = Firebase.auth

        // Configuración del ActivityResultLauncher para el inicio de sesión con Google
        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    signInFirebaseWithGoogle(account.idToken)
                } catch (e: Exception) {
                    // Manejo de errores si ocurre algo durante el proceso de inicio de sesión con Google
                }
            }
        }
    }

    // Función para manejar el inicio de sesión con las credenciales de Google
    private fun signInFirebaseWithGoogle(idToken: String?) {
        val authCredential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser = firebaseAuth.currentUser!!

                    sharedPreferences.edit().apply(){
                        putString(EMAIL,user.email).apply()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Configuración de vistas y botones
    private fun setupViews() {
        // Se habilita/deshabilita el botón de inicio de sesión según los datos ingresados
        binding.etUsername.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateInputs(text.toString(), binding.etPassword.text.toString())
        }
        binding.etPassword.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateInputs(binding.etUsername.text.toString(), text.toString())
        }

        // Manejadores de clics de los botones
        binding.btnLogin.setOnClickListener {
            signInWithEmailPassword()
        }
        binding.btnGoogle.setOnClickListener {
            signInWithGoogle()
        }
        binding.btnRegister.setOnClickListener {
            signUpWithEmailPassword()
        }
    }


    // Función para registrar un nuevo usuario con correo y contraseña
    private fun signUpWithEmailPassword() {
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (validateInputs(email, password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "El usuario no pudo ser creado", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Credenciales no válidas", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para iniciar sesión con correo y contraseña
    private fun signInWithEmailPassword() {
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        signInFirebaseWithEmail(email, password)
    }

    // Función para manejar el inicio de sesión con correo y contraseña a través de Firebase
    private fun signInFirebaseWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "El usuario no se encontró", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Función para iniciar sesión con Google
    private fun signInWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val client: GoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val intent = client.signInIntent
        googleLauncher.launch(intent)
    }

    // Función para validar que los datos ingresados sean válidos
    private fun validateInputs(email: String, password: String): Boolean {
        val isEmailOk = !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordOk = password.length >= 6
        return isPasswordOk && isEmailOk
    }
    companion object{
        const val SESSION_PREFERENCE:String ="SESSION_PREFERENCES"
        const val EMAIL:String ="EMAIL"
    }
}














