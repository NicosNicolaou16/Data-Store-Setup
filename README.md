# Data Store Setup

This project shows the setup for the Jetpack library Preference Data Store.

Target SDK version: 34 <br />
Minimum SDK version: 27 <br />
Kotlin version: 1.9.22 <br />
Gradle version: 8.3.0 <br />

## Step 1:

Add the dependencies library

```Kotlin
val preferencesDataStoreVersion by extra("1.0.0")

dependencies {
    //...
    //Preferences dataStore
    implementation("androidx.datastore:datastore-preferences:$preferencesDataStoreVersion")
}
```

## Step 2:

Create a Helper class that initialize the Data Store

```Kotlin
object PreferencesDataStoreHelper {
    
    private const val PREFERENCES_DATA_STORE_NAME = "preferences_data_store_name"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)

    //Other Code Here...
}
```

## Step 3:

Create the two methods for save and print and for our example we are using only for String value

```Kotlin
object PreferencesDataStoreHelper {

    //Other Code Here...
    
    internal suspend fun saveStringValue(
        value: String,
        key: Preferences.Key<String>,
        context: Context
    ) {
        context.dataStore.edit { saveData ->
            saveData[key] = value
        }
    }

    internal fun getStringValueFlow(key: Preferences.Key<String>, context: Context): Flow<String?> =
        context.dataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { readData ->
                readData[key]
            }
    
    //Other Code Here...
}
```

## Step 4:

Bonus Part - delete specific value and delete all Data Store values

```Kotlin
object PreferencesDataStoreHelper {

    //Other Code Here...
    
    internal suspend fun removeStringValueWithSpecificKey(
        key: Preferences.Key<String>,
        context: Context
    ) {
        context.dataStore.edit { it.remove(key) }
    }

    internal suspend fun removeAllValues(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}
```

## Step 5:

Call the methods for save, print, delete specific value and all values in the screen

```Kotlin
class MainActivity : ComponentActivity() {

    //Other Code Here...

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
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
                    Text(stringResource(R.string.remove_specific_value))
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
}
```

## Check my article

 <br />

# References

<br />