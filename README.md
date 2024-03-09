# Data Store Setup
This project shows the setup for Preference Data Store.

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
private const val PREFERENCES_DATA_STORE_NAME = "preferences_data_store_name"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)
```

## Step 3:
