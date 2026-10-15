# RickAndMorty_Clean

App Android que muestra datos de \`The Rick and Morty API\` con una arquitectura limpia y principios SOLID. Migrada a una arquitectura MVI (unidirectional data flow) y desarrollada con tecnologías modernas de Android.

- Lenguajes: Kotlin / Java
- Build: Gradle
- SDK mínimo/objetivo: Android SDK 34

Características principales
* Arquitectura: MVI (Model - View - Intent) con capas Domain / Data / UI.
* UI: Jetpack Compose para todas las pantallas.
* Inyección de dependencias: Hilt.
* Persistencia local: Room (entities, DAO, \`@Upsert\` donde aplica).
* Red: Retrofit con \`kotlinx.serialization\` (configuración: \`ignoreUnknownKeys = true\`, \`coerceInputValues = true\`).
* Concurrencia: Kotlin Coroutines y \`Flow\`.
* Navegación: Navigation Compose.
* Patrón: Repository + UseCases para separar lógica de dominio.
* Gestión de estado: Unidirectional Data Flow, estados inmutables, efectos secundarios manejados por middleware / side-effects.
* Manejo de caché y reintento: lógica para refrescar desde red cuando la cache está vacía (ej. lanzar excepción específica y usar \`retryWhen\`).
* Tests: unitarios para usecases/repositorio y UI tests para composables (según estén implementados).

Estructura (resumen)
* \`app/src/main/java/com/.../data\`
    - \`local\`: Room entities, DAOs, base de datos.
    - \`network\`: modelos de respuesta, Retrofit service, serialización.
    - \`repository\`: implementaciones que combinan red y local.
* \`app/src/main/java/com/.../domain\`
    - \`model\`, \`usecase\`.
* \`app/src/main/java/com/.../ui\`
    - Composables, MVI: intents/actions, state, reducer, viewmodel (gestiona intents y expone state).
* \`app/src/main/java/com/.../di\`
    - Módulos Hilt para Retrofit, Room, repositorios y usecases.
* \`app/src/main/java/com/.../navigation\`
    - Graph de navegación Compose.

Notas importantes
* Asegúrate de que los nombres de tablas de Room coincidan con los usados en \`@Query\` (usar \`@Entity(tableName = "characters")\` evita conflictos con nombres reservados).
* Configuración de serializador recomendada para tolerancia a cambios en la API:
  ```kotlin
  val json = Json {
      ignoreUnknownKeys = true
      coerceInputValues = true
  }
