package com.ilhomsoliev.conferencestestapp.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ConferenceItem(
    name: String,
    startDate: String,
    endDate: String,
    icon: String,
    objType: String,
    onItemClick: () -> Unit,
) {
    var isImageLoading by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = icon,
    )

    isImageLoading = when (painter.state) {
        is AsyncImagePainter.State.Loading -> true
        else -> false
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                        .height(115.dp)
                        .width(77.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painter,
                    contentDescription = "Poster Image",
                    contentScale = ContentScale.FillBounds,
                )

                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        color = MaterialTheme.colors.primary,
                    )
                }
            }
            Column {
                Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = startDate, color = Color.Gray)
                Text(text = endDate, color = Color.Gray)
                Text(text = objType)
            }
        }
    }
}