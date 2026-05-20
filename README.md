# LAB11 - GoogleMapsLocationApp

## Description

Application Android utilisant Google Maps SDK permettant :

- d’afficher une Google Map,
- de récupérer la position GPS,
- d’écouter les changements de localisation,
- d’ajouter un marker dynamique,
- de déplacer automatiquement la caméra.

Le projet a été réalisé avec Android Studio et un émulateur Android.

---

## Technologies utilisées

- Java
- Android Studio
- Google Maps SDK for Android
- LocationManager
- GPS Provider
- Emulator Android

---

## Fonctionnalités

### Affichage Google Maps
L’application affiche une carte Google Maps dans un `SupportMapFragment`.

### Permissions runtime
L’utilisateur doit accepter la permission de localisation.

### Localisation GPS
L’application écoute les changements de position grâce à :

```java
LocationManager.GPS_PROVIDER
```

### Marker dynamique
Le marker se déplace automatiquement lorsque la position change.

### Zoom automatique
La caméra suit automatiquement la position GPS.

---

## Structure du projet

```text
GoogleMapsLocationApp/
│
├── MapsActivity.java
├── activity_maps.xml
├── AndroidManifest.xml
├── google_maps_api.xml
└── build.gradle
```

---

## Permissions utilisées

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.INTERNET"/>
```

---

## Configuration Google Maps API

1. Créer une clé API Google Maps
2. Activer Maps SDK for Android
3. Ajouter la clé dans :

```xml
google_maps_api.xml
```

---


## Résultat

- Affichage de Google Maps
- Marker GPS
- Déplacement dynamique
- Zoom automatique
- Suivi de position en temps réel

## Vidéo Démonstratif
  

https://github.com/user-attachments/assets/c7d71475-ea2e-4de3-aa37-9209777b898e



---

## Auteur

Projet réalisé par :
- Sana Asseknour
- 4eme année ENSA MARRAKECH - Cybersécurité et systéme de télécommunications.
