package org.gangoogle.project.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpaceW(value: Dp) {
    Spacer(modifier = Modifier.width(value))
}

@Composable
fun SpaceH(value: Dp) {
    Spacer(modifier = Modifier.height(value))
}

