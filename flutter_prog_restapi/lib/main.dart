
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
runApp(const MyApp());
}

class MyApp extends StatelessWidget {
const MyApp({super.key});

@override
Widget build(BuildContext context) {
return MaterialApp(
debugShowCheckedModeBanner: false,
title: 'REST API Demo',
theme: ThemeData(
colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
useMaterial3: true,
),
home: const UserScreen(),
);
}
}

class UserScreen extends StatefulWidget {
const UserScreen({super.key});

@override
State<UserScreen> createState() => _UserScreenState();
}

class _UserScreenState extends State<UserScreen> {
List users = [];
bool isLoading = true;
String errorMessage = '';

Future<void> fetchUsers() async {
setState(() {
isLoading = true;
errorMessage = '';
});

try {
final response = await http.get(
Uri.parse('https://jsonplaceholder.typicode.com/users'),
);

if (response.statusCode == 200) {
setState(() {
users = jsonDecode(response.body);
});
} else {
setState(() {
errorMessage = 'Failed to load data: ${response.statusCode}';
});
}
} catch (e) {
setState(() {
errorMessage = 'Unable to connect. Check your internet connection.';
});
} finally {
setState(() {
isLoading = false;
});
}
}

@override
void initState() {
super.initState();
fetchUsers();
}

@override
Widget build(BuildContext context) {
return Scaffold(
appBar: AppBar(
title: const Text('REST API - User Details'),
backgroundColor: Colors.blue,
foregroundColor: Colors.white,
actions: [
IconButton(
onPressed: fetchUsers,
icon: const Icon(Icons.refresh),
),
],
),
body: isLoading
? const Center(child: CircularProgressIndicator())
    : errorMessage.isNotEmpty
? Center(child: Text(errorMessage))
    : ListView.builder(
itemCount: users.length,
itemBuilder: (context, index) {
final user = users[index];

return Card(
margin: const EdgeInsets.symmetric(
horizontal: 12,
vertical: 6,
),
child: ListTile(
leading: CircleAvatar(
child: Text('${user['id']}'),
),
title: Text(
user['name'] ?? 'No name',
style: const TextStyle(
fontWeight: FontWeight.bold,
),
),
subtitle: Text(user['email'] ?? 'No email'),
trailing: const Icon(Icons.person),
),
);
},
),
floatingActionButton: FloatingActionButton(
onPressed: fetchUsers,
child: const Icon(Icons.download),
),
);
}
}
