# Djoudini's Bar Calculator 🍸

**Der ultimative Begleiter für deine Barbesuche – Behalte den Überblick und werde nie wieder abgezogen!**

Eine moderne Android-App, entwickelt mit Kotlin und Jetpack Compose, die dir hilft, deine Ausgaben zu verfolgen, den Überblick über deinen Promillewert zu behalten und die besten Angebote zu finden.

## ✨ Funktionen

*   **Intelligente Rechnungsverwaltung:**
    *   **Standortbasierte Gruppierung:** Rechnungen werden automatisch nach den von dir besuchten Bars gruppiert, sodass du den Überblick über deine Ausgaben an jedem Ort behältst.
    *   **Getränke hinzufügen:** Füge einfach Getränke aus einer umfangreichen Datenbank hinzu oder erfasse individuelle Preise.
    *   **Persistente Daten:** Deine gesamten Rechnungsdaten und Einstellungen werden automatisch gespeichert und bei jedem Neustart der App geladen, damit du nichts verlierst.

*   **Erweiterter Preischeck:**
    *   Vergleiche den Preis deines Drinks mit dem durchschnittlichen Preis der App-Datenbank.
    *   Intuitive Statusanzeige: "✅ Top Preis!", "⚠️ Normaler Preis.", "🚨 ABZOCKE!"

*   **Präziser Promillerechner:**
    *   Berechne deinen geschätzten Blutalkoholgehalt basierend auf Geschlecht, Gewicht und konsumierten Getränken (Widmark-Formel).
    *   Visualisierung deines Pegels mit dynamischen Farben.

*   **Interaktive Bar-Erkundung:**
    *   **Bars in der Nähe finden:** Nutze die OpenStreetMap (Overpass API), um automatisch Bars in deiner Umgebung zu entdecken und auszuwählen.
    *   **Standortberechtigungen:** Die App fragt nach deiner Zustimmung, um deinen Standort für die Barsuche zu verwenden.

*   **Umfangreiche Getränke-Datenbank:**
    *   Eine riesige Auswahl von **über 250 Getränken** (Cocktails, Biere, Shots, alkoholfreie Getränke), um jeden Geschmack zu treffen.
    *   Jeder Drink mit Details wie Kategorie, durchschnittlichem Preis, Alkoholgehalt und einer Beschreibung.

*   **Weitere Features (To-Do):**
    *   Trinkgeldrechner
    *   Rechnung teilen
    *   Getränk-Erkennung per Foto
    *   Betrunken-Modus
    *   Wasser-Logging

## 🚀 Erste Schritte

### Installation

1.  **APK herunterladen:**
    Die neueste APK-Datei wird automatisch über GitHub Actions erstellt. Gehe zu **Actions** > **Build Android APK** > wähle den neuesten erfolgreichen Build > scrolle nach unten zu "Artifacts" und lade die `app-debug.apk` herunter.

2.  **App installieren:**
    Übertrage die APK auf dein Android-Gerät und installiere sie. Gegebenenfalls musst du die Installation aus "unbekannten Quellen" in deinen Geräteeinstellungen zulassen.

### Verwendung

1.  **Standortberechtigung erteilen:** Beim ersten Start fragt die App nach der Standortberechtigung. Diese ist notwendig, um Bars in deiner Nähe finden zu können.
2.  **Bar auswählen:** Auf dem Home-Screen kannst du auf den "Bar wechseln"-Button klicken, um eine Liste von Bars in deiner Umgebung anzuzeigen. Wähle eine Bar aus oder gib einen Namen manuell ein.
3.  **Getränke zur Rechnung hinzufügen:** Gehe zum "Menü"-Tab, wähle ein Getränk und gib den tatsächlichen Preis ein, um es deiner Rechnung hinzuzufügen.
4.  **Rechnungen einsehen:** Im "Rechnung"-Tab siehst du alle deine Barbesuche gruppiert mit den jeweiligen Getränken und der Gesamtsumme.
5.  **Promillewert prüfen:** Der "Pegel"-Tab zeigt dir deinen geschätzten Promillewert und die Hangover-Gefahr an.

## 🛠️ Entwicklung

### Technologie-Stack

*   **Programmiersprache:** Kotlin
*   **UI-Framework:** Jetpack Compose (Modern Android UI)
*   **Datenpersistenz:** AndroidX DataStore Preferences (für Einstellungen und serialisierte Rechnungsdaten)
*   **Standortdienste:** Google Location Services (für GPS-Koordinaten)
*   **OpenStreetMap API:** Overpass API (für die Suche nach Bars in der Nähe)
*   **HTTP-Client:** OkHttp
*   **JSON-Serialisierung:** Gson
*   **Build-System:** Gradle
*   **CI/CD:** GitHub Actions (für automatische APK-Builds)
*   **Unit Testing:** JUnit (für die Überprüfung der Kernlogik)

### Lokaler Build

Um die App lokal zu kompilieren und eine Debug-APK zu erstellen:

```bash
./gradlew assembleDebug
```

Die erstellte APK findest du dann unter `app/build/outputs/apk/debug/`.

### Tests ausführen

Um die Unit-Tests der App auszuführen:

```bash
./gradlew testDebugUnitTest
```

## 📄 Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert – siehe die [LICENSE](LICENSE)-Datei für Details.
