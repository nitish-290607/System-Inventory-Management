# System Inventory Management System

A Java-based inventory management system with GUI frontend, SQLite database backend, and proper interface architecture.

## Features

- **Item Management**: Add, update, delete, and view inventory items
- **Search & Filter**: Search items by name/description and filter by category
- **Low Stock Alerts**: Configurable threshold-based stock monitoring
- **Reports**: Category breakdown and inventory statistics
- **Database Integration**: SQLite (default) with Beekeeper Studio support

## Quick Start

### Prerequisites
- Java JDK 25 (or 11+)
- Maven 3.6+

### Run the Application
```bash
mvn exec:java
```

Or use the provided script:
```bash
start_app.bat
```

## Project Structure

```
src/main/java/com/inventory/
├── model/          # Data models (Item.java)
├── dao/            # Database access layer
├── service/        # Business logic layer
├── database/       # Database connection
└── gui/            # User interface (Swing)
```

## Database

- **SQLite database** (`inventory.db`) is created automatically
- **Use Beekeeper Studio** to view/edit the database:
  - Connect to SQLite database
  - Browse to project folder and select `inventory.db`
- **Sample data** available in `database_setup.sql`

## Usage

1. **Item Management Tab**: Add new inventory items
2. **Inventory View Tab**: View, search, edit, and delete items  
3. **Reports Tab**: View statistics and low stock alerts

## Development

The application follows proper OOP design with:
- **DAO Pattern** for database access
- **Service Layer** for business logic
- **Interface-based Design** for flexibility
- **Input Validation** and error handling

## Files

- `start_app.bat` - Quick start script
- `setup_environment.bat` - Set permanent PATH variables
- `database_setup.sql` - Database setup and sample data
- `QUICK_START.md` - Quick reference guide