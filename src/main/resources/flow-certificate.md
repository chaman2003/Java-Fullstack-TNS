# Certificate Flow (request-by-request)

This file explains what happens, step-by-step, in the codebase when you run each Certificate API request (including the most recent updates: `qualification` field in the model, gap-free ID logic in services, and frontend mapping in `app.js`). Open the files below to inspect the implementation.

Files to open (quick):
- `src/main/java/com/tns/placementmanagment/entities/Certificate.java` — entity (fields: `id`, `year`, `college`, `qualification`)
- `src/main/java/com/tns/placementmanagment/controllers/CertificateController.java` — REST endpoints mapping
- `src/main/java/com/tns/placementmanagment/services/CertificateService.java` — service interface
- `src/main/java/com/tns/placementmanagment/services/impl/CertificateServiceImpl.java` — service implementation (see `findNextAvailableId()`)
- `src/main/java/com/tns/placementmanagment/repositories/CertificateRepository.java` — JPA repo (see `findAllIds()`)
- `src/main/resources/schema.sql` — DB table definition and sample rows (includes `qualification` column)
- `src/main/resources/static/app.js` and `index.html` — frontend mapping and UI hooks

High-level summary
- Frontend sends HTTP request (from `app.js` or an API client).
- `CertificateController` receives it and delegates to `CertificateService`.
- Service (`CertificateServiceImpl`) enforces business rules (ID assignment for create, existence check for update) and calls `CertificateRepository`.
- Repository persists/reads from PostgreSQL; the `Certificate` entity maps fields to the `certificate` table.
- Response returns to controller, then frontend.

Detailed request flows

1) GET /api/certificate — List all certificates
- Example:
  ```bash
  curl http://localhost:8080/api/certificate
  ```
- Steps:
  1. `app.js` calls `GET /api/certificate` (or you call curl/Postman).
  2. `CertificateController.getCertificates()` executes.
  3. Controller calls `CertificateService.getCertificate()` (service interface).
  4. `CertificateServiceImpl.getCertificate()` calls `certificateRepository.findAll()`.
  5. JPA returns all `Certificate` rows; entities are mapped by `Certificate.java` (note the `qualification` field).
  6. Controller returns the list as JSON to the caller.

2) POST /api/certificate — Create a certificate (gap-free ID assigned)
- Example:
  ```bash
  curl -X POST http://localhost:8080/api/certificate \
    -H "Content-Type: application/json" \
    -d '{"year":2024,"college":"IIT Bombay","qualification":"ML Certificate"}'
  ```
- Steps:
  1. `app.js` (Create form) sends POST JSON to `/api/certificate`.
  2. `CertificateController.createCertificate(@RequestBody Certificate)` receives the JSON and deserializes it into a `Certificate` instance (`Certificate.java` must have setters).
  3. Controller calls `CertificateService.createCertificate(certificate)`.
  4. `CertificateServiceImpl.createCertificate()` calls `findNextAvailableId()` to compute the first missing ID (1,2,3...) by scanning `certificateRepository.findAllIds()` (JPQL query returning ordered IDs).
     - This enforces the configured gap-free ID strategy: deleted slots are filled on subsequent creates.
  5. Service sets `certificate.setId(nextId)` and calls `certificateRepository.save(certificate)`.
  6. JPA inserts the row into PostgreSQL (`schema.sql` defines the `qualification` column); inserted entity returns with the assigned `id`.
  7. Service returns saved entity to controller, controller returns JSON response to frontend.

3) PUT /api/certificate — Update an existing certificate
- Example:
  ```bash
  curl -X PUT http://localhost:8080/api/certificate \
    -H "Content-Type: application/json" \
    -d '{"id":1,"year":2024,"college":"IIT Bombay","qualification":"ML (updated)"}'
  ```
- Steps:
  1. `app.js` Edit form gathers values and sends PUT JSON (must include `id`).
  2. `CertificateController.updateCertificate(@RequestBody Certificate)` deserializes JSON to `Certificate`.
  3. Controller calls `CertificateService.updateCertificate(certificate)`.
  4. `CertificateServiceImpl.updateCertificate()` first checks `certificate.getId() != null` and `certificateRepository.existsById(id)`.
     - If `existsById` returns false, the service returns null and the controller throws an `IllegalArgumentException` (resulting in a 4xx/5xx depending on your exception handling). Ensure request JSON includes `id`.
  5. If exists, service calls `certificateRepository.save(certificate)` to update the row.
  6. Controller returns the updated entity JSON to the caller; frontend refreshes view (`loadRecords()`).

4) DELETE /api/certificate/{id} — Delete a record
- Example:
  ```bash
  curl -X DELETE http://localhost:8080/api/certificate/1
  ```
- Steps:
  1. Frontend or client calls `DELETE /api/certificate/{id}`.
  2. `CertificateController.deleteCertificate(@PathVariable id)` is called.
  3. Controller calls `CertificateService.deleteCertificate(id)`.
  4. `CertificateServiceImpl.deleteCertificate()` calls `certificateRepository.deleteById(id)`; the row is removed from PostgreSQL.
  5. Deleted ID becomes available for reuse; next `POST` will fill the first missing ID because `findNextAvailableId()` scans `findAllIds()`.

Important updated details (quick reference)
- `qualification` was added to `Certificate.java` and to `schema.sql` — include it in POST/PUT payloads and frontend forms.
- Gap-free IDs: `findNextAvailableId()` (in `CertificateServiceImpl`) scans `certificateRepository.findAllIds()` and returns the smallest missing positive integer (1-based). Reused IDs occur only when created via the API (direct DB inserts can bypass this logic).
- Frontend mapping: `src/main/resources/static/app.js` now includes `qualification` in `entityFields.certificate` and uses the `POST/PUT` endpoints described above.
- Controller-level validation: `updateCertificate()` will throw if you attempt to update a non-existing ID; be sure to fetch or check IDs before updating in the UI.

Troubleshooting
- If `qualification` column is missing: restart the app (with `spring.jpa.hibernate.ddl-auto=update`) or run `src/main/resources/schema.sql` manually against your DB.
- If PUT returns 500: confirm the JSON includes `id` and that the `id` exists; check controller logs for exact exception.
- If IDs skip numbers unpredictably: check for direct DB inserts or other services inserting rows without using the gap-free service.

Demo checklist
- Start PostgreSQL and the Spring Boot app.
- Open `http://localhost:8080` to use the UI (`index.html` + `app.js`).
- Use the UI or curl/Postman with the sample payloads above to exercise GET/POST/PUT/DELETE.
- Inspect these files while testing to trace flow: `CertificateController.java`, `CertificateServiceImpl.java`, `CertificateRepository.java`, `Certificate.java`, and `app.js`.

One-line summary
- Frontend -> `CertificateController` -> `CertificateServiceImpl` (ID rules) -> `CertificateRepository` (DB) -> PostgreSQL; `qualification` and gap-free ID logic are the main recent updates.
