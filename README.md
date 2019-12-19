# Android Project – Hiking Buddy App
The Hiking Buddy is an Android app that was developed to be your hiking companion. It helps you track hike information like duration and number of steps taken and also allow you to see a map with your location to make sure you never get lost!

The app uses the following Android features:
- Storage: SQLite & Shared Preferences;
- Sensors: Step Detector, Location;
- Map: MapView powered by MapBox;
- Wake Lock: Using PARTIAL_WAKE_LOOK to avoid device going to sleep;
- Service: Using start service to keep counting steps in the background;
- Broadcast receiver: BOOT_COMPLETED intent_filter so we can start again the step counter
service if the user has rebooted the device while hiking;
- Permissions: INTERNET, WAKE_LOCK, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION;
- Layout: Using Activity, BottonNavigationView and Fragments;

PS: I found a bug on MapBox Android SDK where the user location is not being rendered unless you move to another app and went back. Bug was submitted to their GitHub repository under [Issue #682](https://github.com/mapbox/mapbox-android-demo/issues/682). 
