
##Start React Native Android

```bash
react-native start > /dev/null 2>&1 &
$adb reverse tcp:8081 tcp:8081
```

```bash
$adb shell input keyevent 82
```

upgrad to 0.20.0

```bash
Set "react-native": "^0.20.0", in package.json
Delete node_modules
Run react-native upgrade (updates android/app/build.gradle among others)
Uninstall app on my phone
react-native run-android
```