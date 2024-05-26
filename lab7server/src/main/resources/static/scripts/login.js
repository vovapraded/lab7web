// Получаем элемент чекбокса по его ID
var rememberPasswordCheckbox = document.getElementById("rememberPassword");

// Проверяем, выбран ли чекбокс
if (rememberPasswordCheckbox.checked) {
    // Выполняем действия, если чекбокс выбран
    console.log("Checkbox is checked");
} else {
    // Выполняем действия, если чекбокс не выбран
    console.log("Checkbox is not checked");
}
