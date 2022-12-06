package com.tzh.myshop.ui.shareComponent

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.tzh.myshop.common.ulti.PopUpState
import com.tzh.myshop.common.app.ComposeFileProvider
import com.tzh.myshop.common.app.checkPermission
import com.tzh.myshop.ui.shareComponent.Dimen.paddingDefault

@Composable
fun ChoosePhotoLayout(addImage: (Uri, Int) -> Unit, imageList: List<String?> = emptyList()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChoosePhoto(
            Modifier.weight(1f),
            uri = if (imageList[0] != null) {
                try {
                    Uri.parse(imageList[0])
                } catch (e: Exception) {
                    null
                }
            } else null,
        ) {
            addImage(it, 0)
        }
        ChoosePhoto(
            Modifier.weight(1f),
            uri = if (imageList[1] != null) {
                try {
                    Uri.parse(imageList[1])
                } catch (e: Exception) {
                    null
                }
            } else null,
        ) {
            addImage(it, 1)
        }
        ChoosePhoto(
            Modifier.weight(1f),
            uri = if (imageList[2] != null) {
                try {
                    Uri.parse(imageList[2])
                } catch (e: Exception) {
                    null
                }
            } else null,
        ) {
            addImage(it, 2)
        }
    }
}

@Composable
fun ChoosePhoto(modifier: Modifier = Modifier, uri: Uri?, selectImage: (Uri) -> Unit) {

    var isShowDialog by remember { mutableStateOf(PopUpState.CLOSE) }
    var selectImages by remember { mutableStateOf(uri) }

    Card(
        border = BorderStroke(1.dp, Color.Red),
        elevation = paddingDefault,
        modifier = modifier
            .fillMaxHeight()
            .padding(paddingDefault)
            .clickable {
                isShowDialog = PopUpState.OPEN
            },
    ) {
        if (selectImages == null) {
            Text(
                text = "Click to add photo", Modifier.padding(paddingDefault), textAlign = TextAlign.Center
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(selectImages),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
        }
    }

    when (isShowDialog) {
        PopUpState.OPEN -> {
            ChooseImageDialog(setShowDialog = {
                isShowDialog = it
            }, setImage = {
                if (it != null) {
                    selectImages = it
                    selectImage(it)
                }
            })
        }
        PopUpState.CLOSE -> {
        }
    }
}

@Composable
fun ChooseImageDialog(setShowDialog: (PopUpState) -> Unit, setImage: (Uri?) -> Unit) {
    val context = LocalContext.current
    var hasImage by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }
    var imageUri: Uri? = null

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uriList ->
        imageUri = uriList
        hasImage = true
        setImage(imageUri)
        setShowDialog(PopUpState.CLOSE)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        hasImage = success
        if (hasImage) {
            setImage(imageUri)
            Log.e("SAVE FILE", imageUri.toString())
        }
        setShowDialog(PopUpState.CLOSE)
    }

    if (isPermissionGranted) {
        checkPermission() { isGranted ->
            if (isGranted) {
                imageUri = ComposeFileProvider.getImageUri(context)
                cameraLauncher.launch(imageUri)
            } else {
                Toast.makeText(
                    context, "When you take photo camera permission is needed,please grant camara permission", Toast.LENGTH_LONG
                ).show()
                isPermissionGranted = false
            }
        }
    }
    Dialog(onDismissRequest = { setShowDialog(PopUpState.CLOSE) }) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {
            Box() {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Select Picture", style = MaterialTheme.typography.h5)
                    Dimen.DefaultMarginHeight()
                    MyShopButton.TextButton(
                        onClick = {
                            isPermissionGranted = true
                        }, title = "Take Photo", modifier = Modifier.fillMaxWidth()
                    )
                    Dimen.DefaultMarginHeight()
                    MyShopButton.TextButton(
                        onClick = {
                            galleryLauncher.launch("image/*")
                        }, title = "Choose from Library", modifier = Modifier.fillMaxWidth()
                    )

                    Dimen.DefaultMarginHeight()
                    MyShopButton.TextButton(
                        onClick = {
                            setShowDialog(PopUpState.CLOSE)
                        }, title = "Cancel", modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
