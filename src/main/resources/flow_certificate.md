# Certificate Flow (concise)

Purpose
- Quick reference showing how the Certificate feature flows through the codebase.
- Use this when you need to explain, debug, or demo the Certificate entity.

Where to open (in order)
1. `src/main/java/com/tns/placementmanagment/entities/Certificate.java` — entity fields
2. `src/main/java/com/tns/placementmanagment/controllers/CertificateController.java` — REST endpoints
3. `src/main/java/com/tns/placementmanagment/services/CertificateService.java` — service interface
4. `src/main/java/com/tns/placementmanagment/services/impl/CertificateServiceImpl.java` — business logic (see `findNextAvailableId()`)
5. `src/main/java/com/tns/placementmanagment/repositories/CertificateRepository.java` — DB access (see `findAllIds()` query)
6. `src/main/resources/schema.sql` — DB table definition & sample rows
7. `src/main/resources/static/app.js` and `index.html` — frontend form field & UI hooks

High-level flow (one-line each)
- Frontend → sends HTTP request to `POST/PUT/GET/DELETE /api/certificate`.
- Controller receives request and delegates to Service.
- Service enforces business logic (ID assignment, validation) and calls Repository.
- Repository (Spring Data JPA) reads/writes to PostgreSQL.
- DB persists the record; response flows back to frontend.

Key files & quick notes
- `CertificateController.java`
  - `@PostMapping` — create
  - `@PutMapping` — update (throws if id missing/not found)
  - `@GetMapping` — list all
  - `@DeleteMapping(path="/{id}")` — delete

- `CertificateServiceImpl.java`
  - `createCertificate(Certificate)` → calls `findNextAvailableId()` then `certificateRepository.save()`
  - `findNextAvailableId()` → scans `certificateRepository.findAllIds()` to produce gap-free IDs (1,2,3,... filling deleted slots)
  - `updateCertificate()` → checks `existsById(id)` before saving

- `CertificateRepository.java`
  - `List<Long> findAllIds()` — custom JPQL query ordering ids ascending; required by gap-filling logic
  - Standard JpaRepository methods are available (`save`, `findAll`, `deleteById`, `existsById`)

- `Certificate.java` (fields):
  - `id: Long` (primary key)
  - `year: int`
  - `college: String`
  - `qualification: String` (newly added)

- `schema.sql` updates
  - `certificate` table includes `qualification VARCHAR(255)`
  - sample inserts include `qualification` values

Frontend mapping (files & fields)
- `src/main/resources/static/app.js` — `entityFields.certificate` now contains:
  - `year`, `college`, `qualification`
- `index.html` uses `handleCreate(event)` and `loadRecords()` to display and refresh records

Sample curl commands (quick demo)
- Create:
  ```bash
  curl -X POST http://localhost:8080/api/certificate \
    -H "Content-Type: application/json" \
    -d '{"year":2024,"college":"IIT Bombay","qualification":"ML Certificate"}'
  ```
- Get all:
  ```bash
  curl http://localhost:8080/api/certificate
  ```
- Update:
  ```bash
  curl -X PUT http://localhost:8080/api/certificate \
    -H "Content-Type: application/json" \
    -d '{"id":1,"year":2024,"college":"IIT Bombay","qualification":"ML (updated)"}'
  ```
- Delete:
  ```bash
  curl -X DELETE http://localhost:8080/api/certificate/1
  ```

Where to demo in Postman/UI
- Postman collection: create a collection called `Certificate` and add the four requests above.
- UI: open `http://localhost:8080` → select `certificate` entity → use Create form and Refresh to show results.

Troubleshooting tips (concise)
- If new `qualification` column is missing, ensure app restarted with `spring.jpa.hibernate.ddl-auto=update`, or run `src/main/resources/schema.sql` manually.
- If PUT returns 500, check request JSON contains `id` and matches existing id; controller throws when update returns null.
- If IDs jump, the gap-free logic may be bypassed by direct DB inserts; prefer API for consistency.

One-paragraph summary (deliverable)
- Certificate operations are exposed via `/api/certificate`. The controller routes requests to `CertificateServiceImpl` where `create` auto-assigns a gap-free ID (using `findAllIds()` from the repository) and saves via JPA. The frontend (`app.js`/`index.html`) provides forms and lists which call these endpoints; `schema.sql` documents the DB shape. For demos, open `index.html`, use the UI and Postman examples above.

-----

File created: `src/main/resources/flow_certificate.md` — concise, developer-friendly flow ready for demos.
