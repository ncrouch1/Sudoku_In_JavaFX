module com.example.sudoku_in_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudoku_in_javafx to javafx.fxml;
    exports com.example.sudoku_in_javafx;
}