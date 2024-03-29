package com.nicos.datastoresetup

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nicos.datastoresetup.preferencesDataStore.PreferencesDataStoreHelper
import com.nicos.datastoresetup.ui.theme.DataStoreSetupTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val PREFERENCE_STRING_KEY = "preference_string_key"

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
                    DataStoreMainView()
                }
            }
        }
    }
}

@Composable
fun DataStoreMainView() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(contentAlignment = Alignment.Center) {
        Column {
            Button(
                onClick = {
                    scope.launch {
                        /**
                         * Save the Value
                         * */
                        PreferencesDataStoreHelper.saveStringValue(
                            "testValue",
                            stringPreferencesKey(PREFERENCE_STRING_KEY),
                            context
                        )
                    }
                },
                modifier = Modifier.width(170.dp)
            ) {
                Text(stringResource(R.string.save_string_value))
            }
            Button(
                onClick = {
                    scope.launch {
                        /**
                         * Read the Value
                         * */
                        PreferencesDataStoreHelper.getStringValueFlow(
                            stringPreferencesKey(PREFERENCE_STRING_KEY),
                            context
                        ).collect {
                            if (it != null) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.width(170.dp)
            ) {
                Text(stringResource(R.string.print_string_value))
            }
            Button(
                onClick = {
                    scope.launch {
                        /**
                         * Remove a Specific Value
                         * */
                        PreferencesDataStoreHelper.removeStringValueWithSpecificKey(
                            stringPreferencesKey(PREFERENCE_STRING_KEY),
                            context
                        )
                    }
                },
                modifier = Modifier.width(170.dp)
            ) {
                Text(stringResource(R.string.remove_specific_value), textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    scope.launch {
                        /**
                         * Remove all the Values
                         * */
                        PreferencesDataStoreHelper.removeAllValues(
                            context
                        )
                    }
                },
                modifier = Modifier.width(170.dp)
            ) {
                Text(stringResource(R.string.remove_all_values))
            }
        }
    }
}

@Composable
@Preview
fun MainViewPreview() {
    DataStoreMainView()
}