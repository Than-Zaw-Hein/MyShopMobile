package com.tzh.myshop.common.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkPermission(setIsGranted: ((Boolean) -> Unit)? = null) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            setIsGranted?.invoke(true)
            print("  // Open camera")
        } else {
            setIsGranted?.invoke(false)
        }
    }

    LaunchedEffect(
        key1 = permissionState,
        block = {
            checkAndRequestCameraPermission(context, Manifest.permission.CAMERA, launcher) {
                setIsGranted?.invoke(it)
            }
        },
    )
}

fun checkAndRequestCameraPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    setIsGranted: ((Boolean) -> Unit)? = null
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult != PackageManager.PERMISSION_GRANTED) {
        launcher.launch(permission)
    } else {
        setIsGranted?.invoke(true)
    }
}
