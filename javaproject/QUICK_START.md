# Quick Start Guide

## Fastest Way to Run the Application

### Option 1: If you have Maven installed
```cmd
mvn clean compile exec:java
```

### Option 2: If you have an IDE (IntelliJ, Eclipse, NetBeans)
1. Import the project (File → Open/Import)
2. Run `MainFrame.java` class

### Option 3: Manual setup (No Maven required)
1. **Download SQLite JDBC driver**:
   - Go to: https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
   - Create `lib` folder in project root
   - Save the JAR file as `lib/sqlite-jdbc-3.42.0.0.jar`

2. **Run the launcher**:
   ```cmd
   launch.bat
   ```

## What You'll See

The application opens with 3 main tabs:

1. **Item Management** - Add new inventory items
2. **Inventory View** - View, search, edit, and delete items  
3. **Reports** - View statistics and low stock alerts

## First Steps

1. **Add some items** in the Item Management tab
2. **View your inventory** in the Inventory View tab
3. **Check reports** to see statistics

## Database Integration

- **SQLite database** (`inventory.db`) is created automatically
- **Use Beekeeper Studio** to view/edit the database directly:
  - Open Beekeeper Studio
  - Connect to SQLite database
  - Browse to your project folder and select `inventory.db`

## Sample Data

To add sample data quickly, you can:
1. Use the GUI to add items manually, or
2. Run the SQL commands from `database_setup.sql` in Beekeeper Studio

## Need Help?

- Check `SETUP_INSTRUCTIONS.md` for detailed setup
- Check `README.md` for complete documentation
- All source code is well-commented for learning