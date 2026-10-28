
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart' as p;

void main() {
WidgetsFlutterBinding.ensureInitialized();
runApp(const MyApp());
}

// Main application
class MyApp extends StatelessWidget {
const MyApp({super.key});

@override
Widget build(BuildContext context) {
return MaterialApp(
title: 'Student CRUD',
debugShowCheckedModeBanner: false,
theme: ThemeData(
colorScheme: ColorScheme.fromSeed(
seedColor: Colors.indigo,
),
useMaterial3: true,
),
home: const StudentScreen(),
);
}
}

// SQLite database operations
class StudentDatabase {
static Database? _database;

static Future<Database> getDatabase() async {
if (_database != null) {
return _database!;
}

final databasePath = await getDatabasesPath();
final path = p.join(databasePath, 'students.db');

_database = await openDatabase(
path,
version: 1,
onCreate: (Database db, int version) async {
await db.execute('''
          CREATE TABLE students (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            marks TEXT NOT NULL
          )
        ''');
},
);

return _database!;
}

// Insert student
static Future<int> insertStudent(
String name,
String marks,
) async {
final db = await getDatabase();

return await db.insert(
'students',
{
'name': name,
'marks': marks,
},
);
}

// Get all students
static Future<List<Map<String, dynamic>>> getStudents() async {
final db = await getDatabase();

return await db.query(
'students',
orderBy: 'id DESC',
);
}

// Update student
static Future<int> updateStudent(
int id,
String name,
String marks,
) async {
final db = await getDatabase();

return await db.update(
'students',
{
'name': name,
'marks': marks,
},
where: 'id = ?',
whereArgs: [id],
);
}

// Delete student
static Future<int> deleteStudent(int id) async {
final db = await getDatabase();

return await db.delete(
'students',
where: 'id = ?',
whereArgs: [id],
);
}
}

// Student screen
class StudentScreen extends StatefulWidget {
const StudentScreen({super.key});

@override
State<StudentScreen> createState() => _StudentScreenState();
}

class _StudentScreenState extends State<StudentScreen> {
final TextEditingController nameController = TextEditingController();
final TextEditingController marksController = TextEditingController();

List<Map<String, dynamic>> students = [];

int? selectedId;
bool isLoading = true;
bool isSaving = false;

@override
void initState() {
super.initState();
loadStudents();
}

// Display all students
Future<void> loadStudents() async {
try {
final data = await StudentDatabase.getStudents();

if (!mounted) return;

setState(() {
students = data;
isLoading = false;
});
} catch (e) {
if (!mounted) return;

setState(() {
isLoading = false;
});

showMessage('Could not load students: $e');
}
}

// Insert or update a student
Future<void> saveStudent() async {
final name = nameController.text.trim();
final marks = marksController.text.trim();

if (name.isEmpty || marks.isEmpty) {
showMessage('Please enter both student name and marks.');
return;
}

setState(() {
isSaving = true;
});

try {
if (selectedId == null) {
await StudentDatabase.insertStudent(name, marks);

if (!mounted) return;

showMessage('Student inserted successfully!');
} else {
await StudentDatabase.updateStudent(
selectedId!,
name,
marks,
);

if (!mounted) return;

showMessage('Student updated successfully!');
}

clearForm();
await loadStudents();
} catch (e) {
if (!mounted) return;

showMessage('Operation failed: $e');
} finally {
if (mounted) {
setState(() {
isSaving = false;
});
}
}
}

// Load selected student into the form
void editStudent(Map<String, dynamic> student) {
setState(() {
selectedId = student['id'] as int;
nameController.text = student['name'].toString();
marksController.text = student['marks'].toString();
});

showMessage('Edit the details and press Update.');
}

// Delete a student
Future<void> removeStudent(int id) async {
try {
await StudentDatabase.deleteStudent(id);

if (!mounted) return;

if (selectedId == id) {
clearForm();
}

await loadStudents();

if (!mounted) return;

showMessage('Student deleted successfully!');
} catch (e) {
if (!mounted) return;

showMessage('Could not delete student: $e');
}
}

// Clear input fields
void clearForm() {
nameController.clear();
marksController.clear();

setState(() {
selectedId = null;
});
}

// Show a message using Flutter BuildContext
void showMessage(String message) {
if (!mounted) return;

ScaffoldMessenger.of(context).showSnackBar(
SnackBar(
content: Text(message),
behavior: SnackBarBehavior.floating,
),
);
}

// Ask before deleting
Future<void> confirmDelete(int id, String name) async {
final bool? confirm = await showDialog<bool>(
context: context,
builder: (BuildContext dialogContext) {
return AlertDialog(
title: const Text('Delete Student?'),
content: Text('Do you want to delete $name?'),
actions: [
TextButton(
onPressed: () {
Navigator.of(dialogContext).pop(false);
},
child: const Text('Cancel'),
),
FilledButton(
onPressed: () {
Navigator.of(dialogContext).pop(true);
},
child: const Text('Delete'),
),
],
);
},
);

if (confirm == true) {
await removeStudent(id);
}
}

@override
void dispose() {
nameController.dispose();
marksController.dispose();
super.dispose();
}

@override
Widget build(BuildContext context) {
return Scaffold(
appBar: AppBar(
title: const Text(
'Student CRUD',
style: TextStyle(
fontWeight: FontWeight.bold,
color: Colors.white,
),
),
centerTitle: true,
backgroundColor: Colors.indigo,
foregroundColor: Colors.white,
),
body: SafeArea(
child: Column(
children: [
// Input form
Card(
margin: const EdgeInsets.all(16),
elevation: 4,
child: Padding(
padding: const EdgeInsets.all(16),
child: Column(
children: [
Text(
selectedId == null
? 'Add New Student'
    : 'Update Student',
style: const TextStyle(
fontSize: 20,
fontWeight: FontWeight.bold,
),
),
const SizedBox(height: 16),

TextField(
controller: nameController,
decoration: const InputDecoration(
labelText: 'Student Name',
hintText: 'Enter student name',
prefixIcon: Icon(Icons.person),
border: OutlineInputBorder(),
),
),

const SizedBox(height: 12),

TextField(
controller: marksController,
keyboardType: const TextInputType.numberWithOptions(
decimal: true,
),
decoration: const InputDecoration(
labelText: 'Marks',
hintText: 'Enter student marks',
prefixIcon: Icon(Icons.school),
border: OutlineInputBorder(),
),
),

const SizedBox(height: 16),

Row(
children: [
Expanded(
child: FilledButton.icon(
onPressed: isSaving ? null : saveStudent,
icon: Icon(
selectedId == null
? Icons.add
    : Icons.update,
),
label: Text(
selectedId == null ? 'Insert' : 'Update',
),
),
),
const SizedBox(width: 10),
OutlinedButton.icon(
onPressed: clearForm,
icon: const Icon(Icons.clear),
label: const Text('Clear'),
),
],
),
],
),
),
),

// Student list heading
Padding(
padding: const EdgeInsets.symmetric(
horizontal: 16,
vertical: 8,
),
child: Row(
children: [
const Icon(
Icons.people,
color: Colors.indigo,
),
const SizedBox(width: 8),
const Expanded(
child: Text(
'Student Records',
style: TextStyle(
fontSize: 19,
fontWeight: FontWeight.bold,
),
),
),
Text(
'Total: ${students.length}',
style: const TextStyle(
fontWeight: FontWeight.w600,
),
),
IconButton(
onPressed: loadStudents,
tooltip: 'Refresh records',
icon: const Icon(Icons.refresh),
),
],
),
),

// Student list
Expanded(
child: isLoading
? const Center(
child: CircularProgressIndicator(),
)
    : students.isEmpty
? const Center(
child: Column(
mainAxisSize: MainAxisSize.min,
children: [
Icon(
Icons.school_outlined,
size: 64,
color: Colors.grey,
),
SizedBox(height: 12),
Text(
'No students found.',
style: TextStyle(fontSize: 17),
),
SizedBox(height: 4),
Text('Add a student using the form above.'),
],
),
)
    : ListView.builder(
padding: const EdgeInsets.fromLTRB(
12,
0,
12,
16,
),
itemCount: students.length,
itemBuilder: (context, index) {
final student = students[index];
final int id = student['id'] as int;
final String name =
student['name'].toString();
final String marks =
student['marks'].toString();

return Card(
margin: const EdgeInsets.symmetric(
vertical: 6,
),
child: ListTile(
leading: CircleAvatar(
backgroundColor: Colors.indigo.shade100,
child: Text(
'$id',
style: const TextStyle(
color: Colors.indigo,
fontWeight: FontWeight.bold,
),
),
),
title: Text(
name,
style: const TextStyle(
fontWeight: FontWeight.bold,
),
),
subtitle: Text('Marks: $marks'),
onTap: () => editStudent(student),
trailing: IconButton(
tooltip: 'Delete student',
icon: const Icon(
Icons.delete,
color: Colors.red,
),
onPressed: () {
confirmDelete(id, name);
},
),
),
);
},
),
),
],
),
),
);
}
}
