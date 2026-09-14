package com.example.geekappsrift

import android.content.Context
import android.hardware.input.InputManager
import android.view.InputDevice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

// USB vendor IDs used to tell brands apart from InputDevice — the same
// controller can enumerate as a "gamepad" and/or "joystick" source, brand
// detection only cares about who made it.
private const val VENDOR_SONY = 0x054C
private const val VENDOR_MICROSOFT = 0x045E
private const val VENDOR_NINTENDO = 0x057E

enum class ControllerBrand {
    PLAYSTATION,
    XBOX,
    NINTENDO
}

// Face-button glyphs per brand for the confirm/back actions shown in the
// bottom hint bar. Generic/unrecognized gamepads fall back to Xbox, since
// that's the layout most third-party pads mimic.
data class ControllerGlyphs(val confirm: String, val back: String, val secondary: String)

fun ControllerBrand.glyphs(): ControllerGlyphs = when (this) {
    ControllerBrand.PLAYSTATION -> ControllerGlyphs(confirm = "✕", back = "○", secondary = "△")
    ControllerBrand.NINTENDO -> ControllerGlyphs(confirm = "B", back = "A", secondary = "X")
    ControllerBrand.XBOX -> ControllerGlyphs(confirm = "A", back = "B", secondary = "Y")
}

private fun detectControllerBrand(context: Context): ControllerBrand {
    for (deviceId in InputDevice.getDeviceIds()) {
        val device = InputDevice.getDevice(deviceId) ?: continue
        val isGamepad = (device.sources and InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD
        val isJoystick = (device.sources and InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK
        if (!isGamepad && !isJoystick) continue

        return when (device.vendorId) {
            VENDOR_SONY -> ControllerBrand.PLAYSTATION
            VENDOR_NINTENDO -> ControllerBrand.NINTENDO
            VENDOR_MICROSOFT -> ControllerBrand.XBOX
            else -> ControllerBrand.XBOX // generic pads mostly speak Xbox's layout
        }
    }
    return ControllerBrand.XBOX
}

// Tracks the currently connected gamepad's brand, updating live as
// controllers are plugged/unplugged/swapped.
@Composable
fun rememberControllerBrand(): ControllerBrand {
    val context = LocalContext.current
    var brand by remember { mutableStateOf(detectControllerBrand(context)) }

    DisposableEffect(context) {
        val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
        val listener = object : InputManager.InputDeviceListener {
            override fun onInputDeviceAdded(deviceId: Int) {
                brand = detectControllerBrand(context)
            }
            override fun onInputDeviceRemoved(deviceId: Int) {
                brand = detectControllerBrand(context)
            }
            override fun onInputDeviceChanged(deviceId: Int) {
                brand = detectControllerBrand(context)
            }
        }
        inputManager.registerInputDeviceListener(listener, null)
        onDispose { inputManager.unregisterInputDeviceListener(listener) }
    }

    return brand
}
