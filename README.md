# Data Store Setup

[![Linktree](https://img.shields.io/badge/linktree-1de9b6?style=for-the-badge&logo=linktree&logoColor=white)](https://linktr.ee/nicos_nicolaou)
[![Site](https://img.shields.io/badge/Site-blue?style=for-the-badge&label=Web)](https://nicosnicolaou16.github.io/)
[![X](https://img.shields.io/badge/X-%23000000.svg?style=for-the-badge&logo=X&logoColor=white)](https://twitter.com/nicolaou_nicos)
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/nicos-nicolaou-a16720aa)
[![Medium](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@nicosnicolaou)
[![Mastodon](https://img.shields.io/badge/-MASTODON-%232B90D9?style=for-the-badge&logo=mastodon&logoColor=white)](https://androiddev.social/@nicolaou_nicos)
[![Bluesky](https://img.shields.io/badge/Bluesky-0285FF?style=for-the-badge&logo=Bluesky&logoColor=white)](https://bsky.app/profile/nicolaounicos.bsky.social)
[![Dev.to blog](https://img.shields.io/badge/dev.to-0A0A0A?style=for-the-badge&logo=dev.to&logoColor=white)](https://dev.to/nicosnicolaou16)
[![YouTube](https://img.shields.io/badge/YouTube-%23FF0000.svg?style=for-the-badge&logo=YouTube&logoColor=white)](https://www.youtube.com/@nicosnicolaou16)
[![Google Developer Profile](https://img.shields.io/badge/Developer_Profile-blue?style=for-the-badge&label=Google)](https://g.dev/nicolaou_nicos)

A comprehensive guide and implementation example for setting up **Jetpack Preference DataStore** in Android. This project demonstrates how to handle persistent data using a modern, type-safe, and asynchronous approach.

> [!IMPORTANT]  
> Check out the full deep-dive article for this setup:  
> 👉 **[Preferences Data Store Setup - Medium](https://medium.com/@nicosnicolaou/preferences-data-store-setup-b197e3db09dd)** 👈


## 🛠️ Implementation Guide

### Step 1:

Add the dependencies library

#### Kotlin

```Kotlin
val preferencesDataStoreVersion by extra("1.2.0")

dependencies {
    //...
    //Preferences dataStore
    implementation("androidx.datastore:datastore-preferences:$preferencesDataStoreVersion")
}
```

#### libs.versions.toml

```toml
[versions]
# other versions here...
dataStore = "1.2.0"

[libraries]
# other libraries here...
preference-data-store = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "dataStore" }
```

#### Application Gradle:

```Kotlin
implementation(libs.preference.data.store)
```

### Step 2:

Create a Helper class that initialize the Data Store

```Kotlin
object PreferencesDataStoreHelper {

    private const val PREFERENCES_DATA_STORE_NAME = "preferences_data_store_name"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)

    //Other Code Here...
}
```

### Step 3:

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

### Step 4:

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

### Step 5:

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

## 🔧 Versioning

- **Data Store Version:** **1.2.0**
- **Target SDK:** **36**
- **Minimum SDK:** **29**
- **Kotlin Version:** **2.3.10**
- **Gradle Version:** **9.0.1**

## 📚 References & Resources

- [Android Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

## ⭐ Stargazers

If you enjoy this project, please give it a star!
Check out all the stargazers
here: [Stargazers on GitHub](https://github.com/NicosNicolaou16/Data-Store-Setup/stargazers)

## 🙏 Support & Contributions

This project is actively maintained. Feedback, bug reports, and feature requests are welcome! Please feel free to **open an issue** or submit a **pull request**.