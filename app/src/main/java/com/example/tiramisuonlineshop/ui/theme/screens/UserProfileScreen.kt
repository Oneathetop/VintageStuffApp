package com.example.tiramisuonlineshop.ui.theme.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.tiramisuonlineshop.model.Comment
import com.example.tiramisuonlineshop.model.CommentRepo
import com.example.tiramisuonlineshop.model.FileUtils
import com.example.tiramisuonlineshop.model.Suggestion
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("UseKtx")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(navController: NavHostController) {
    val uriSaver = listSaver<Uri?, String>(
        save = { listOf(it.toString()) },
        restore = { if (it.isNotEmpty()) it[0].toUri() else null }
    )
    var profileImageUri by rememberSaveable(stateSaver = uriSaver) {mutableStateOf<Uri?>(null)}  // Holds captured image URI
    var fullName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val fieldSpacing = if (isLandscape) 24.dp else 12.dp
    val scrollState = rememberScrollState()
    var showError by remember { mutableStateOf(false) }
    var showConfirmationCard by remember { mutableStateOf(false) }
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    var firestoreName by remember { mutableStateOf("Loading...") }
    val context = LocalContext.current


    var fetchedComment by rememberSaveable { mutableStateOf<Comment?>(null) }


    // Launcher to capture camera image
    //
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            profileImageUri = saveBitmapToCache(context, bitmap)
        }
    }

    //

    // Launcher to request permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch()
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Camera permission denied")
            }
        }
    }

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    user?.let {
        db.collection("users").document(it.uid).get()
            .addOnSuccessListener { document ->
                firestoreName = document?.getString("Full Name") ?: "No name found"
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching user data", e)
                firestoreName = "Error loading name"
            }
    }


    Scaffold(
        topBar = {
            TopAppBar(

                title = { Text("Welcome, $firestoreName", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    //style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier=Modifier.padding(top = 16.dp)) },
                actions = {
                    TextButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout,contentDescription = "Logout")
                    }
                })
        },
        bottomBar = {
            BottomNavigationBar(navController,currentRoute)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //  Profile Image Display
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                profileImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Upload Button
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Upload Profile Pic")
            }

            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Shipping Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(fieldSpacing))

            Button(
                onClick = {
                    if (fullName.isBlank() || phoneNumber.isBlank() || address.isBlank()) {
                        showError = true
                    } else {
                        showConfirmationCard = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Profile info saved!")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = showConfirmationCard) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clickable { showConfirmationCard = false },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Saved Profile", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("👤 Name: $fullName")
                        Text("📞 Phone: $phoneNumber")
                        Text("🏠 Address: $address")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        val comment = CommentRepo.CommentRepository.fetchRandomComment()
                        withContext(Dispatchers.Main) {
                            fetchedComment = comment
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Load Random Comment")
            }

            fetchedComment?.let { comment ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "From: ${comment.email}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = comment.body, textAlign = TextAlign.Center)
            }


            var enableSuggestions by rememberSaveable { mutableStateOf(false) }
            var suggestionText by rememberSaveable { mutableStateOf("") }
            var savedSuggestion by remember { mutableStateOf<String?>(null) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            )

            {
                RadioButton(
                    selected = enableSuggestions,
                    onClick = { enableSuggestions = !enableSuggestions }
                )
                Text("Write a Suggestion")
            }

            if (enableSuggestions) {
                OutlinedTextField(
                    value = suggestionText,
                    onValueChange = { suggestionText = it },
                    label = { Text("Your Suggestion") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    Button(onClick = {
                        if (suggestionText.isNotBlank()) {
                            FileUtils.saveSuggestion(context, Suggestion(suggestionText))
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Suggestion saved locally!")
                            }
                            suggestionText = "" // clear after saving
                        }
                    }) {
                        Text("Send")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(onClick = {
                        val suggestion = FileUtils.readSuggestion(context)
                        savedSuggestion = suggestion?.text
                    }) {
                        Text("Read")
                    }
                }

                savedSuggestion?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Saved Suggestion: $it")
                }
            }


            Spacer(modifier = Modifier.height(36.dp))

            if (showError) {
                AlertDialog(
                    onDismissRequest = { showError = false },
                    title = { Text("Missing Information") },
                    text = { Text("Please fill all fields.") },
                    confirmButton = {
                        TextButton(onClick = { showError = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}
