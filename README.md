# bysykkel-kodeoppgave
Liten android-app for å se bysykkel-stasjoner i Oslo

## Bruk av appen
Applikasjonen består av én skjerm med:
 - en liste over bysykkel-stasjoner med navn, antall ledige sykler og antall ledige plasser for tilbakelevering av sykkel
 - en knapp for å oppdatere lista
 
## Oppbygging av koden
- `com.example.cyclebikes`
  - `MainActivity` er applikasjonens eneste aktivitet, og denne setter `activity_main.xml` som contentView.
  - `.model`
    - `.network` inneholder `SanntidService` og `SanntidServiceGenerator.kt`. Disse filene bruker retrofit og Moshi for å bygge opp objektet `SanntidApi` som kobler appen opp mot bysykkel-APIet. I tillegg inneholder den `SanntidServiceObject.kt`, som består av dataklasser som representerer responsen vi forventer fra APIet.
    - `Objects.kt` definerer dataklassene `Result` (inneholder status på API-kall) og `Station` (representasjon av bysykkel-stasjon slik den skal vises i appen).
    - `BindingUtils.kt` definerer databindinger for å formattere visning av antall ledige sykler og antall ledige "docks" basert på et Stasjon-objekt.
  - `.locator` inneholder
    - `LocatorViewModel` som er view model for dene ene skjermen som applikasjonen består av. Denne bruker `SanntidApi`-objektet for å hente ned data, og setter dataen sammen til en liste av ´Station´-objekter
    - `LocatorRecyclerViewAdapter` som tar dataene i view modelen og sørger for at disse kan vises frem i et `RecyclerView` (se `fragment_locator.xml` under)
    - `LocatorFragment` som kobler sammen view, viewModel og adapter, ved å observere lista av stasjoner i view modelen og servere denne til adapteret, når dataene endres.
- `res.layout`
  - `activity_main.xml` er en FragmentContainer som inneholder hele UIet i appen. Den peker videre på `fragment_locator.xml`
  - `fragment_locator.xml` inneholder knapp for å oppdatere data, RecyclerView med liste av stasjoner (som igjen inneholder `station_item.xml`) og en ProgressBar/laste-indikator som `LocatorFragment`-kontrolleren setter synlig/usynlig basert på status av nettverks-kall.
  - `station_item.xml` inneholder, for hvert enkelt Stasjon-objekt i lista, et CardView med navn, antall tilgjengelige sykler og antall tilgjengelige plasser for å sette fra seg sykkel

## Kjøre applikasjonen
Last ned (og evt pakk ut) koden, naviger til rot-mappen `CycleBikes` og kjør `gradlew assembleDebug` for å bygge en `.apk`-fil. Eventuelt åpne prosjektet i Android Studio og kjør applikasjonen eller bygg APK derfra.
