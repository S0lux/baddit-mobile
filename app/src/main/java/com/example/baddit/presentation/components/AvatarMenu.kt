package com.example.baddit.presentation.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.baddit.R
import com.example.baddit.ui.theme.CustomTheme.mutedAppBlue
import kotlinx.coroutines.launch


@Composable
fun AvatarMenu(show: Boolean = false) {


    if (show == false) {
        return
    } else {
        Popup(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 25.dp, bottom = 25.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterStart)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Profile",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Row(modifier = Modifier.align(Alignment.Start)) {
                        Image(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Black),
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            alignment = Alignment.Center,
                            contentDescription = "Profile Picture",
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Username",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                            )
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                            )
                        }
                    }

                    Column(modifier = Modifier.fillMaxHeight()) {

                        ProfileItem(painterResource(id = R.drawable.comment), "Test")
                        ProfileItem(painterResource(id = R.drawable.comment), "Test")
                        ProfileItem(painterResource(id = R.drawable.comment), "Test")

                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.mutedAppBlue),
                            onClick = { /*TODO*/ }) {
                            Text(text = "Logout")
                        }
                    }
                }
            }
        )
    }


}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AvatarMenuPreview() {
    AvatarMenu(true);
}