# FindIt — Campus Lost & Found

A beginner-friendly campus lost and found app using plain HTML/CSS/JavaScript, Spring Boot, Maven, and MySQL.

## Requirements

- JDK 17 or newer (Spring Boot 3.4 supports Java 17+)
- MySQL Server 8+
- Internet access on first launch (the included Maven wrapper downloads Maven and project libraries)
- VS Code with Extension Pack for Java and Spring Boot Extension Pack

The workspace recommends these extensions in `.vscode/extensions.json`.

## Database setup

In MySQL Workbench or a MySQL terminal, run `schema.sql`. The app uses database `findit_db` and user `root`. Set the MySQL password you chose during installation in your VS Code PowerShell terminal (do not paste it into chat):

```powershell
$env:DB_USER='root'
$env:DB_PASSWORD=Read-Host 'MySQL root password'
```

Spring JPA updates the tables at startup. The SQL file shows the intended schema.

## Run in VS Code

1. Open this project folder in VS Code.
2. Start MySQL Server and confirm `findit_db` exists.
3. In VS Code, open **Terminal → New Terminal**.
4. Run `.\run-findit.ps1` and enter your MySQL root password at the hidden prompt.
5. Wait for `Started FindItApplication` and visit <http://localhost:8081>.
6. Sign in with either demo account below, or register a campus account, then report and search for items.

## Demo accounts

The app creates these accounts automatically the first time it starts against a database that does not already contain them:

| Role | Email | Password |
|---|---|---|
| Student | `student@findit.local` | `Student@123` |
| Staff | `staff@findit.local` | `Staff@123` |

The login page also has buttons to fill these credentials. They are fixed local demo credentials; replace or remove them before exposing the app publicly. New registrations receive the Student role.

You can also package it with `.\mvnw.cmd clean package` after setting `DB_PASSWORD` in the terminal.

## Features

- Registration and login with BCrypt hashed passwords and session cookies
- Lost/found posts with validated local image uploads (5 MB max)
- Dynamic name, category, type, and location search
- Item detail pages and private in-app contact messages
- Personal dashboard, status updates, edits, and owner-only deletion
- Admin-only user listing endpoint

For an administrator account, first register normally, then promote the account in MySQL Workbench:

```sql
UPDATE users SET role='ADMIN' WHERE email='admin@your-campus.edu';
```

Admin users can delete any post using the same dashboard API; the admin user listing is available at `/api/admin/users`.

## Uploads and configuration

Images are stored in the local `uploads/` folder, and only their generated filenames are saved in MySQL. Configure another directory with `UPLOAD_DIR`. Do not commit real database passwords or uploaded personal images.

## Troubleshooting

- **`mvnw.cmd` cannot download Maven:** check internet access; the wrapper downloads Maven from Maven Central the first time it runs.
- **Access denied for user `root`:** set `DB_PASSWORD` to the password configured for MySQL in this PowerShell terminal before starting the app.
- **Cannot connect to MySQL:** start the MySQL service and verify `DB_URL`, `DB_USER`, and `DB_PASSWORD`.
- **Port 8081 is in use:** choose another port with `--server.port=8082`, then open `http://localhost:8082`.
- **Login says credentials are incorrect:** register the account first and use the same email/password.
- **Image won't upload:** use JPG, PNG, WEBP, or GIF smaller than 5 MB.

## Project layout

```text
src/main/java/com/findit/        Spring Boot app, entities, repositories, controllers, configuration
src/main/resources/static/       Browser pages, styles, and JavaScript
src/main/resources/application.properties
schema.sql                       MySQL schema
pom.xml                          Maven dependencies and build configuration
```
