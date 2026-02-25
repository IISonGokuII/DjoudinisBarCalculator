# Djoudini's Bar Calculator 🍸

**Der ultimative Barrechner - Nie wieder abgezogen werden!**

Eine Android-App, die dir hilft, in Cocktailbars den Überblick zu behalten.

## Features

### Rechnungsrechner
- Getränke zur Rechnung hinzufügen (100 Cocktails + 50 Biere)
- Eigene Getränke mit Preis eingeben
- Gesamtübersicht in Echtzeit

### Preischeck
- Vergleiche den Preis deines Drinks mit dem Durchschnitt
- Ampelsystem: Fair / Teuer / Abzocke
- Datenbank mit 100 Top-Cocktails und 50 deutschen Bieren

### Promillerechner
- Berechne deinen geschätzten Promillewert
- Widmark-Formel mit Geschlecht und Gewicht
- Zeit-bis-nüchtern Anzeige

### Trinkgeldrechner
- Prozentual oder aufgerundet
- Vorschläge zum Aufrunden

### Rechnung teilen
- Teile die Rechnung auf mehrere Personen
- Optional mit Trinkgeld

### Getränk-Erkennung
- Foto-basierte Farberkennung
- Manuelle Identifikation über Farbe & Glastyp
- Vorschläge mit Trefferwahrscheinlichkeit

### Betrunken-Modus
- Extra große Buttons für unsichere Finger

## Getränke-Datenbank

### Top 100 Cocktails
Klassiker, Tiki, Longdrinks, Sours, Shots, Champagner-Cocktails und mehr.

### Top 50 Deutsche Biere
Pilsener, Weizen, Helles, Kölsch, Altbier, Schwarzbier, Craft Beer und mehr.

## APK Download

Die APK wird automatisch über GitHub Actions gebaut. Gehe zu **Actions** > **Build Android APK** > wähle den neuesten Build > lade das Artifact herunter.

## Technologie

- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)
- **Android:** WebView-Wrapper mit nativer Kamera-Integration
- **Build:** Gradle + GitHub Actions
- **Design:** Dark Theme mit Gold-Akzenten

## Build

```bash
./gradlew assembleDebug
```

Die APK findest du dann unter `app/build/outputs/apk/debug/`.

## Lizenz

MIT License
