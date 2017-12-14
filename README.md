
# react-native-ca-mas

## Getting started

`$ npm install react-native-ca-mas --save`

### Mostly automatic installation

`$ react-native link react-native-ca-mas`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-ca-mas` and add `RNCaMas.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNCaMas.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNCaMasPackage;` to the imports at the top of the file
  - Add `new RNCaMasPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-ca-mas'
  	project(':react-native-ca-mas').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ca-mas/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-ca-mas')
  	```

### Configure your project

#### iOS
1. Open Xcode's "Build Settings" tab in your project and add `../node_modules/react-native-ca-mas/ios/frameworks` path to "Framework Search Paths" setting.  
Open Xcode's "General" tab in your project and add the all `.framework` of the `../node_modules/react-native-ca-mas/ios/frameworks` folder in "Embedded Binaries" setting.  

2. do steps 2 of the official documentation:  
[step 2](http://mas.ca.com/docs/ios/1.5.00/guides/#step-2-configure-xcode-properties-for-the-mobile-sdk)  

3. Put your `msso_config.json` in `ios/`. [Reference](http://mas.ca.com/docs/ios/1.5.00/guides/#step-3-add-the-msso_configjson-file)

#### Android
1. In your project, update `app/build.gradle`:
```
android {
    compileSdkVersion 26
    buildToolsVersion "26.0.1"

    defaultConfig {
        minSdkVersion 19
        // other configs
    }

    // other configs
}
```

2. Put the `msso_config.json` in `android/src/main/assets/`.

3. Append the following lines to `MainApplication.java`:
```
import com.ca.mas.foundation.MAS;

  @Override
  public void onCreate() {
    super.onCreate();
    MAS.start(getApplicationContext());
    // other methods
  }
```

## Usage
[Go to Wiki](https://github.com/ArmandoAssuncao/react-native-ca-mas/wiki)

## Troubleshooting

1. if your Android project cause error like:  
`DexIndexOverflowException: Cannot merge new index 65565 into a non-jumbo instruction!`  
add in your `app/build.gradle`:
```
android {
    dexOptions {
        jumboMode true
    }
```

1. if your Android project cause error like:  
`com.android.build.api.transform.TransformException: com.android.ide.common.process.ProcessException: java.util.concurrent.ExecutionException: com.android.dex.DexIndexOverflowException: field ID not in [0, 0xffff]: 65536`
add in your `app/build.gradle`:  
```
android {
    defaultConfig {
        multiDexEnabled true
        ...
    }
```
