package com.example.web_services_httpurlconnection;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnGetData;
    Button btnPostData;
    Button btnPutData;
    Button btnDeleteData;

    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find views
        btnGetData = findViewById(R.id.btnGetData);
        btnPostData = findViewById(R.id.btnPostData);
        btnPutData = findViewById(R.id.btnPutData);
        btnDeleteData = findViewById(R.id.btnDeleteData);

        txtResult = findViewById(R.id.txtResult);


        // =====================================================
        // GET
        // =====================================================

        btnGetData.setOnClickListener(v -> {

            new Thread(() -> {

                try {

                    URL url = new URL(
                            "https://jsonplaceholder.typicode.com/posts/1"
                    );

                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");

                    int responseCode =
                            connection.getResponseCode();

                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            connection.getInputStream()
                                    )
                            );

                    StringBuilder response =
                            new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    connection.disconnect();

                    runOnUiThread(() -> {

                        txtResult.setText(
                                "GET Response Code: "
                                        + responseCode
                                        + "\n\n"
                                        + "Response:\n"
                                        + response
                        );

                    });

                } catch (Exception e) {

                    runOnUiThread(() -> {

                        txtResult.setText(
                                "GET Error:\n"
                                        + e.getMessage()
                        );

                    });

                }

            }).start();

        });


        // =====================================================
        // POST
        // =====================================================

        btnPostData.setOnClickListener(v -> {

            new Thread(() -> {

                try {

                    URL url = new URL(
                            "https://jsonplaceholder.typicode.com/posts"
                    );

                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");

                    connection.setRequestProperty(
                            "Content-Type",
                            "application/json; charset=UTF-8"
                    );

                    connection.setDoOutput(true);


                    String jsonData =
                            "{"
                                    + "\"title\":\"Misbah\","
                                    + "\"body\":\"Android Web Service\","
                                    + "\"userId\":112"
                                    + "}";


                    OutputStream outputStream =
                            connection.getOutputStream();

                    outputStream.write(
                            jsonData.getBytes("UTF-8")
                    );

                    outputStream.close();


                    int responseCode =
                            connection.getResponseCode();


                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            connection.getInputStream()
                                    )
                            );

                    StringBuilder response =
                            new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    connection.disconnect();


                    runOnUiThread(() -> {

                        txtResult.setText(
                                "POST Response Code: "
                                        + responseCode
                                        + "\n\n"
                                        + "Response:\n"
                                        + response
                        );

                    });

                } catch (Exception e) {

                    runOnUiThread(() -> {

                        txtResult.setText(
                                "POST Error:\n"
                                        + e.getMessage()
                        );

                    });

                }

            }).start();

        });


        // =====================================================
        // PUT / UPDATE
        // =====================================================

        btnPutData.setOnClickListener(v -> {

            new Thread(() -> {

                try {

                    URL url = new URL(
                            "https://jsonplaceholder.typicode.com/posts/1"
                    );

                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("PUT");

                    connection.setRequestProperty(
                            "Content-Type",
                            "application/json; charset=UTF-8"
                    );

                    connection.setDoOutput(true);


                    String jsonData =
                            "{"
                                    + "\"id\":1,"
                                    + "\"title\":\"Updated Misbah\","
                                    + "\"body\":\"Updated Android Data\","
                                    + "\"userId\":112"
                                    + "}";


                    OutputStream outputStream =
                            connection.getOutputStream();

                    outputStream.write(
                            jsonData.getBytes("UTF-8")
                    );

                    outputStream.close();


                    int responseCode =
                            connection.getResponseCode();


                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            connection.getInputStream()
                                    )
                            );

                    StringBuilder response =
                            new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    connection.disconnect();


                    runOnUiThread(() -> {

                        txtResult.setText(
                                "PUT / UPDATE Response Code: "
                                        + responseCode
                                        + "\n\n"
                                        + "Response:\n"
                                        + response
                        );

                    });

                } catch (Exception e) {

                    runOnUiThread(() -> {

                        txtResult.setText(
                                "PUT Error:\n"
                                        + e.getMessage()
                        );

                    });

                }

            }).start();

        });


        // =====================================================
        // DELETE
        // =====================================================

        btnDeleteData.setOnClickListener(v -> {

            new Thread(() -> {

                try {

                    URL url = new URL(
                            "https://jsonplaceholder.typicode.com/posts/1"
                    );

                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("DELETE");

                    int responseCode =
                            connection.getResponseCode();

                    connection.disconnect();


                    runOnUiThread(() -> {

                        txtResult.setText(
                                "DELETE Response Code: "
                                        + responseCode
                                        + "\n\n"
                                        + "Post ID 1 deleted successfully."
                        );

                    });

                } catch (Exception e) {

                    runOnUiThread(() -> {

                        txtResult.setText(
                                "DELETE Error:\n"
                                        + e.getMessage()
                        );

                    });

                }

            }).start();

        });

    }
}