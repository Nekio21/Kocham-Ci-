package com.nekio.kot

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityOptionsCompat
import com.nekio.kot.ui.theme.KotTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    val arrayImage = arrayOf(
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4,
        R.drawable.cat5,
        R.drawable.cat6,
        R.drawable.cat7,
        R.drawable.cat8,
        R.drawable.cat9,
        R.drawable.cat10,
        R.drawable.cat11,
        R.drawable.cat12,
        R.drawable.cat13,
        R.drawable.cat14,
        R.drawable.cat15,
        R.drawable.cat16,
        R.drawable.cat17,
        R.drawable.cat18,
        R.drawable.cat19,
    )

    val arrayText = arrayOf(
        "Kocham Cię :)",
        "Jesteś piękna",
        "Jesteś warta wszystkiego co najlepsze",
        "<3 <3 <3 <3",
        "Super jesteś",
        "Jesteś ważniejsza niż krówka",
        "Jesteś uosobieniem klasy i wdzięku",
        "Twoja inteligencja i mądrość są inspirujące",
        "Twoja determinacja i pasja są godne podziwu",
        "Jesteś kwintesencją wdzięku i klasy",
        "Twoja uroda jest jak dzieło sztuki",
        "Jesteś jak najsłodszy deser"
    )

    private val phoneNumber = "WPISAC TUTAJ NUMER UKOCHANEJ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KotTheme {
                Cat()
            }
        }
    }



    private fun sendSMS(context: Context) {

        val smsManager:SmsManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
             smsManager = context.getSystemService(SmsManager::class.java)

        } else {
            smsManager =  SmsManager.getDefault()
        }

        arrayText.shuffle()

        val arraySMS = smsManager.divideMessage(arrayText[0])

        smsManager.sendMultipartTextMessage(phoneNumber, null, arraySMS, null, null)

        Toast.makeText(this, arrayText[0], Toast.LENGTH_SHORT).show()

    }

    @Composable
    fun Cat() {
        val context = LocalContext.current
        var image by remember { mutableIntStateOf(arrayImage[0]) }

        val smsPermission = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {

                    runOnUiThread( Runnable() {
                        run() {
                            sendSMS(context)
                        }
                    });

                    arrayImage.shuffle()
                    image = arrayImage[0]
                } else {
                    Toast.makeText(this, "Nie ma zgody :( na wyslanie SMS", Toast.LENGTH_SHORT).show()
                }
            }
        )

        Column {
            Image(
                painter = painterResource(image),
                contentDescription = "kot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        smsPermission.launch(
                            Manifest.permission.SEND_SMS
                        )
                    })
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        KotTheme {
            Cat()
        }
    }
}

