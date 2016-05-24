
##Start React Native Android

```bash
react-native start > /dev/null 2>&1 &
$adb reverse tcp:8081 tcp:8081
```

```bash
$adb shell input keyevent 82
```
