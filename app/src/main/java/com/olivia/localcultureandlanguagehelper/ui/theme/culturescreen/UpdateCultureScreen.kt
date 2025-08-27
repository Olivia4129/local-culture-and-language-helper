package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.olivia.localcultureandlanguagehelper.data.CultureViewModel
import com.olivia.localcultureandlanguagehelper.models.Culture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCultureScreen(
    navController: NavHostController,
    cultureId: String
) {
    val context = LocalContext.current
    val cultureViewModel = CultureViewModel(navController, context)

    // States for culture data
    var name by remember { mutableStateOf("") }
    var tribe by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var event by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Fetch culture from Firebase
    LaunchedEffect(cultureId) {
        cultureViewModel.databaseReference.child(cultureId).get()
            .addOnSuccessListener { snapshot ->
                val culture = snapshot.getValue(Culture::class.java)
                culture?.let {
                    it.id = cultureId
                    name = it.name
                    tribe = it.tribe
                    description = it.description
                    event = it.event
                    imageUrl = it.imageUrl // load existing image if present
                }
            }
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Culture", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4CAF50))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Culture Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tribe,
                onValueChange = { tribe = it },
                label = { Text("Tribe") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = event,
                onValueChange = { event = it },
                label = { Text("Event / Festival") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Show selected or existing image
            val imageSource: Any? = imageUri ?: imageUrl
            if (imageSource != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageSource),
                    contentDescription = "Culture Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Change Image")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                    cultureViewModel.updateCulture(
                        cultureId = cultureId,
                        name = name,
                        tribe = tribe,
                        description = description,
                        event = event,
                        imageUri = imageUri,
                        currentUserId = currentUserId // ✅ pass userId
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Culture")
            }

        }
    }
}
