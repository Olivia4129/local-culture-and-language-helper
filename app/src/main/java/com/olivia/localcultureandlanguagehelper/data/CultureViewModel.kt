package com.olivia.localcultureandlanguagehelper.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.database.*
import com.olivia.localcultureandlanguagehelper.models.Culture
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_VIEWCULTURE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.InputStream

class CultureViewModel(
    private val navController: NavHostController,
    private val context: Context
)  {

    val databaseReference = FirebaseDatabase.getInstance().getReference("Culture")

    private val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dwnh5yt1t/image/upload"
    private val uploadPreset = "cultures"

    private val _cultures = mutableStateListOf<Culture>()
    val cultures: List<Culture> = _cultures

    // ✅ Upload Culture (always stores pushKey in "id")
    fun uploadCulture(
        imageUri: Uri?,
        name: String,
        tribe: String,
        description: String,
        event: String,

    ) {
        val ref = databaseReference.push()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) } ?: ""

                val cultureData = mapOf(
                    "id" to (ref.key ?: ""),
                    "name" to name,
                    "tribe" to tribe,
                    "description" to description,
                    "event" to event,
                    "imageUrl" to imageUrl,
                //   // ✅ store userId consistently
                )

                ref.setValue(cultureData).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Culture saved successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTE_VIEWCULTURE)
                    } else {
                        Toast.makeText(context, "Error: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // ✅ Fetch All Cultures
    fun allCultures(
        culture: MutableState<Culture>,
        cultures: SnapshotStateList<Culture>
    ): SnapshotStateList<Culture> {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cultures.clear()
                for (snap in snapshot.children) {
                    val retrievedCulture = snap.getValue(Culture::class.java)
                    if (retrievedCulture != null) {
                        culture.value = retrievedCulture
                        cultures.add(retrievedCulture)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
        return cultures
    }

    // ✅ Delete Culture (expects pushKey, not tribe/name/etc.)

    fun deleteCulture(cultureKey: String) {
        databaseReference.child(cultureKey).removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Culture deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
    }

    // ✅ Update Culture (expects pushKey as cultureId)
    fun updateCulture(
        cultureId: String,
        name: String,
        tribe: String,
        description: String,
        event: String,
        imageUri: Uri?,
        currentUserId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newImageUrl = imageUri?.let { uploadToCloudinary(context, it) }

                val updates = mutableMapOf<String, Any>(
                    "id" to cultureId,
                    "name" to name,
                    "tribe" to tribe,
                    "description" to description,
                    "event" to event,
                    "userId" to currentUserId
                )

                if (!newImageUrl.isNullOrEmpty()) {
                    updates["imageUrl"] = newImageUrl
                }

                databaseReference.child(cultureId).updateChildren(updates).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Culture updated successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEWCULTURE)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // ✅ Upload to Cloudinary
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw Exception("Image read failed")

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "image.jpg",
                fileBytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Upload failed")

        val responseBody = response.body?.string() ?: throw Exception("Empty Cloudinary response")
        val json = JSONObject(responseBody)
        return json.optString("secure_url", "")
            .takeIf { it.isNotEmpty() }
            ?: throw Exception("Failed to get image URL")
    }
}
