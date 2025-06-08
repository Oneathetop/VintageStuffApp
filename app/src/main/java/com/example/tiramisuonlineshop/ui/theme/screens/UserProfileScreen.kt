package com.example.tiramisuonlineshop.ui.theme.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.launch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.input.KeyboardType
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(navController: NavHostController) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var snackbarHostState = remember { SnackbarHostState() }
    var coroutineScope = rememberCoroutineScope()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val fieldSpacing = if (isLandscape) 24.dp else 12.dp
    val scrollState = rememberScrollState()
    var showError by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = bitmap.let {
                ImageRequest.Builder(context)
                    .data(it)
                    .size(Size.ORIGINAL)
                    .build()
                    .data.toString().toUri()
            }
            profileImageUri = uri
        }
    }

    Scaffold(

        topBar = {
            //TopAppBar(title = { Text("User Profile") })
            TopAppBar(title = { (com.example.tiramisuonlineshop.R.string.user_profile) })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        snackbarHost = {SnackbarHost(snackbarHostState)}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        //contentDescription = "Profile Image",
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                //label = { Text("Full Name") },
                label = { com.example.tiramisuonlineshop.R.string.full_name },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                //label = { Text("Phone Number") },
                label = { com.example.tiramisuonlineshop.R.string.phone_number},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(fieldSpacing))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                //label = { Text("Shipping Address") },
                label = { com.example.tiramisuonlineshop.R.string.shipping_address },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(fieldSpacing))
            //
            Button(
                onClick = {
                    if (fullName.isBlank() || phoneNumber.isBlank() || address.isBlank()) {
                        showError = true
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Profile saved successfully!")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Profile")
            }

            if (showError) {
                AlertDialog(
                    onDismissRequest = { showError = false },
                    //title = { Text("Missing Information") },
                    title = { com.example.tiramisuonlineshop.R.string.info_error },
                    //text = { Text("Please fill all fields.") },
                    text = { com.example.tiramisuonlineshop.R.string.missing_info },
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
