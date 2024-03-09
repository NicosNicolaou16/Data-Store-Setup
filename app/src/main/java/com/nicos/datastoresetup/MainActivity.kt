package com.nicos.datastoresetup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicos.datastoresetup.ui.theme.DataStoreSetupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreSetupTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Button(
                onClick = {},
                modifier = Modifier.width(170.dp)
            ) {
                Text("Save String Value")
            }
            Button(
                onClick = {},
                modifier = Modifier.width(170.dp)
            ) {
                Text("Save Specific Value")
            }
            Button(
                onClick = {},
                modifier = Modifier.width(170.dp)
            ) {
                Text("Save All Values")
            }
        }
    }
}

@Composable
@Preview
fun MainViewPreview() {
    MainView()
}