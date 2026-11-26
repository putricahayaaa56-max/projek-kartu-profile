package com.example.belajarmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ProfilSaya(this)
            }
        }
    }
}

@Composable
fun ProfilSaya(context: Context) {

    val prefs = remember {
        context.getSharedPreferences(
            "data_profil",
            Context.MODE_PRIVATE
        )
    }

    var nama by remember {
        mutableStateOf(prefs.getString("nama", "") ?: "")
    }

    var email by remember {
        mutableStateOf(prefs.getString("email", "") ?: "")
    }

    var tahun by remember {
        mutableStateOf(prefs.getString("tahun", "") ?: "")
    }

    var pesan by remember {
        mutableStateOf("")
    }

    val hijauMuda = Color(0xFFE8F7D9)
    val hijau = Color(0xFFA3C58C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(hijauMuda)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "🐈",
            fontSize = 85.sp
        )

        Text(
            text = "Profil Saya",
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Mahasiswa Informatika",
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.height(45.dp))

        // INPUT NAMA
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            placeholder = {
                Text("Nama lengkap")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(18.dp))

        // INPUT EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text("Alamat email")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // INPUT TAHUN LAHIR
        OutlinedTextField(
            value = tahun,
            onValueChange = {
                if (it.length <= 4 &&
                    it.all { karakter -> karakter.isDigit() }
                ) {
                    tahun = it
                }
            },
            placeholder = {
                Text("Tahun Lahir (mis. 2006)")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(35.dp))

        // TOMBOL BATAL DAN SIMPAN
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Button(
                onClick = {
                    nama = ""
                    email = ""
                    tahun = ""
                    pesan = ""
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text("BATAL", color = Color.Black)
            }

            Button(
                onClick = {

                    val namaInput = nama.trim()
                    val emailInput = email.trim()
                    val tahunInput = tahun.trim()

                    when {

                        namaInput.isEmpty() -> {
                            pesan = "Nama harus diisi!"
                        }

                        !Patterns.EMAIL_ADDRESS
                            .matcher(emailInput).matches() -> {
                            pesan = "Email tidak valid!"
                        }

                        tahunInput.length != 4 -> {
                            pesan = "Tahun lahir harus 4 digit!"
                        }

                        else -> {

                            // SIMPAN DATA
                            val berhasil = prefs.edit()
                                .putString("nama", namaInput)
                                .putString("email", emailInput)
                                .putString("tahun", tahunInput)
                                .commit()

                            if (berhasil) {

                                // KIRIM DATA KE SECOND ACTIVITY
                                val intent = Intent(
                                    context,
                                    SecondActivity::class.java
                                )

                                intent.putExtra(
                                    SecondActivity.EXTRA_NAMA,
                                    namaInput
                                )

                                intent.putExtra(
                                    SecondActivity.EXTRA_EMAIL,
                                    emailInput
                                )

                                intent.putExtra(
                                    "extra_tahun",
                                    tahunInput
                                )

                                context.startActivity(intent)

                            } else {
                                pesan = "Data gagal disimpan!"
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = hijau
                )
            ) {
                Text("SIMPAN", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // HAPUS DATA TERSIMPAN
        Button(
            onClick = {
                prefs.edit().clear().apply()

                nama = ""
                email = ""
                tahun = ""
                pesan = "Data berhasil dihapus!"
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = hijau
            )
        ) {
            Text(
                "HAPUS DATA TERSIMPAN",
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // LIHAT PENGGUNA
        Button(
            onClick = {
                val intent = Intent(
                    context,
                    UserListActivity::class.java
                )
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = hijau
            )
        ) {
            Text(
                "LIHAT PENGGUNA",
                color = Color.Black
            )
        }

        if (pesan.isNotEmpty()) {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = pesan,
                color = Color.Red
            )
        }
    }
}